/*
 * class for managing datas
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DataManager {
    private String filepath;
    private File datas;
    private FileReader datasReader;
    public DataManager(String filepath) throws FileNotFoundException, IOException {
        this.filepath = filepath;
        this.datas = new File(filepath);

        if(!datas.isFile()){
            datas.createNewFile(); // ioexception
        }

        datasReader = new FileReader(datas); // fileNotFoundException
    }

    // ajout d'un Account
    public void addAccount(Account acc) throws IOException{
        if(datas.canWrite()){
            FileWriter datasWriter = new FileWriter(datas);
            System.out.println("amorcage du processus d'écruture mon coco");
            datasWriter.write(acc.toString()+ " \n");
            // datasWriter.write("BOnjour");
            System.out.println("Fin de l'écriture");
            datasWriter.close();
        }else{
            throw new IOException("Erreur l'ors de l'écriture des données dans un fichier");
        }
    }

    // lecture des données de chaque compte

}
