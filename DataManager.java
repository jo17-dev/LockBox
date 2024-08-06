/*
 * class for managing datasFile
 */

import java.io.EOFException;
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

        // check if the datas file exists
        if(!datasFile.isFile() || !datasFile.exists()){
            datasFile.createNewFile(); // ioexception
        }
    }

    // ajout d'un Account
    public void addAccount(Account acc) throws IOException{
        if(datasFile.canWrite()){

            System.out.println(" ---------- Amorage du processus d'écriture --------");
            for(Account item : datas){
                if(item.getId() == acc.getId() || item.getLabel().toLowerCase() == acc.getLabel().toLowerCase()){ // if there is another account with the same label, we just cancel it
                    throw new IOException("Erreur de traitement: Le Ce compte existe déjas:: " + item.getId() + " == " + acc.getId() );
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
        this.datas = downloadDatasFromStream(datasFile);
        return this.datas;
    }

    // remplacement de la totalité de comptes
    public void setAccounts(ArrayList<Account> target) throws LockBoxException{
        this.datas = target;
        uploadDatasToStream(datasFile, datas);
    }

    // mise a jour d'un compte:
    public void updateAccount (int id, Account updatedAccount) throws LockBoxException{
        int targetedIndex=-1;
        for(Account item : datas){
            targetedIndex++;
            if(item.getId() == updatedAccount.getId() || item.getLabel().toLowerCase() == updatedAccount.getLabel().toLowerCase()){ // if there is another
                break;
            }
        }
        if(targetedIndex < datas.size()){
            try{
                datas.add(targetedIndex, updatedAccount);
                datas.remove(targetedIndex+1);
            }catch(IndexOutOfBoundsException e){
                throw new LockBoxException("Erreur survenue l'ors de la mise à jour du compte données");
            }
        }else{
            throw new LockBoxException("Impossible de mettre à jour vos données");
        }
    }

    // supression d'un compte: mise a jour des données, supression et mise à jour 
    public boolean deleteAccount(int id){
        boolean result = false;
        uploadDatasToStream(datasFile, datas);
        datas = downloadDatasFromStream(datasFile);
        for(Account item : datas){
            if(item.getId() == id){
                result = datas.remove(item);
                break;
            }
        }
        uploadDatasToStream(datasFile, datas);
        datas = downloadDatasFromStream(datasFile);
        return result;
    }

    // enregistrement des données dans les fichiers
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

    // Récupération des données dans les fichiers
    private static ArrayList<Account> downloadDatasFromStream(File dbFile){
        ObjectInputStream datasReader;
        try{
            ArrayList<Account> result = new ArrayList<Account>();
            datasReader = new ObjectInputStream(new FileInputStream(dbFile));
            while (true) {
                try{
                    result.add(
                        (Account) datasReader.readObject()
                    );
                }catch(EOFException exception){
                    datasReader.close();
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

    public void saveAndDisconect(){
        uploadDatasToStream(datasFile, datas);
    }
}
