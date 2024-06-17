/*
 * class for managing datasFile
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataManager {
    private String filepath;
    private File datasFile;
    private ArrayList<Account> datas;

    public DataManager(String filepath) throws FileNotFoundException, IOException, LockBoxException {
        this.filepath = filepath;
        this.datasFile = new File(this.filepath);

        datas = downloadDatasFromStream(datasFile);

        // check if the file exists
        if(!datasFile.isFile() || !datasFile.exists()){
            datasFile.createNewFile(); // ioexception
        }
    }

    // ajout d'un Account
    public void addAccount(Account acc) throws IOException{
        if(datasFile.canWrite()){

            System.out.println(" ---------- Amorage du processus d'écriture --------");
            for(Account item : datas){
                if(item.getLabel().equals(acc.getLabel())){ // if there is another accoun with the same lable, we just cancel it
                    throw new IOException("Erreur de traitement: Le Ce compte existe déjas existe déjas");
                }
            }
            datas.add(acc);
            uploadDatasToStream(datasFile, datas);
            System.out.println(" ------- Fin du process d'écriture ------ ");
        }else{
            throw new IOException("Erreur l'ors de l'écriture des données dans un fichier");
        }
    }

    // Lecture des données d'un compte
    public ArrayList<Account> getAccounts (){
        return downloadDatasFromStream(datasFile);
    }

    private static void uploadDatasToStream(File dbFile, ArrayList<Account> accounts){
        try{
           ObjectOutputStream datasWriter = new ObjectOutputStream(new FileOutputStream(dbFile));
            for(Account item : accounts){
                datasWriter.writeObject(item);
            }
            datasWriter.close();
        }catch(IOException ioe){
            System.out.println("Error while updating datas to database");
        }
    }

    private static ArrayList<Account> downloadDatasFromStream(File dbFile){
        try{
            ArrayList<Account> result = new ArrayList<Account>();
            ObjectInputStream datasReader = new ObjectInputStream(new FileInputStream(dbFile));
            while (true) {
                try{
                    result.add(
                        (Account) datasReader.readObject()
                    );
                }catch(Exception exception){
                    break;
                }
            }
            datasReader.close();
            return result;
        }catch(Exception e){
            
            System.out.println("Downloading falied");
        }
        return new ArrayList<Account>();
    }
}
