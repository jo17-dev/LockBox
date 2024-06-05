/*
 * class for managing datasFile
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
// import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;

public class DataManager {
    private String filepath;
    private File datasFile;
    private ObjectOutputStream datasWriter;
    private ObjectInputStream datasReader;
    private ArrayList<Account> datas;

    public DataManager(String filepath) throws FileNotFoundException, IOException, LockBoxException {
        this.filepath = filepath;
        this.datasFile = new File(this.filepath);
        datasWriter = new ObjectOutputStream(new FileOutputStream(datasFile));
        datasReader = new ObjectInputStream(new FileInputStream(datasFile));
        datas = new ArrayList<Account>();


        // check if the file exists
        if(!datasFile.isFile()){
            datasFile.createNewFile(); // ioexception
        }
    }

    // ajout d'un Account
    public void addAccount(Account acc) throws IOException{
        if(datasFile.canWrite()){
            // ObjectOutputStream 
            System.out.println(" ---------- Amorage du processus d'écriture --------");

            datasWriter.writeObject((Account) acc);
            System.out.println(" ------- Fin du process d'écriture ------ ");
        }else{
            throw new IOException("Erreur l'ors de l'écriture des données dans un fichier");
        }
    }

    // Lecture des données d'un compte
    public ArrayList<Account> getAccounts (){
        // ArrayList<Account> result = new ArrayList<Account>();
        while (true) {
            try{
                Account premier = (Account) datasReader.readObject();
                System.out.println(premier.toString());
                datas.add(premier);
            }catch(IOException ioe){
                System.out.println("erreur de lecture - fin de la lecture");
                break;
            }catch(ClassNotFoundException cnfe){
                System.out.println("Classe Account non trouvée");
                break;
            }
        }
        
        // System.out.println(result.size());
        return datas;
    }

    // fermeture des streams:
    public boolean closeStreams(){
        try{
            datasWriter.close();
            return false;
        }catch(IOException ioe){
            return true;
        }
    }
}
