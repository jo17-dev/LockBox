/*
 * class to manage each Idc from each account
 */

import java.util.ArrayList;

public class Account extends BasicComponent {
    private int id; // unique id of the account
    public static int accountTotalNumber;
    private ArrayList<Idc> idcs;
    private ArrayList<String> tags;

    public Account(int id, String label){
        super(label); // name of the account; eg: facebook, instagram, etc..
        this.id = id;
        idcs = new ArrayList<Idc>();
        tags = new ArrayList<String>();
        accountTotalNumber++;
    }

    public int getId(){
        return id;
    }

    public ArrayList<Idc> getIdcs(){
        return idcs;
    }

    public ArrayList<String> getTags(){
        return tags;
    }

    public void delIdc(String idcLabel){
        for(Idc item: idcs){
            if(item.getLabel() == idcLabel){
                idcs.remove(item);
            }
            break;
        }
    }

    public void addIdc(Idc pIdc) throws BitLockerException{
        for(Idc item: idcs){
            if(item.getLabel().equals(pIdc.getLabel().toLowerCase())){
                throw new BitLockerException("L'identifiant de connxion "+ item.getLabel()+ " existe dejas" );
            }
        }
        idcs.add(pIdc);
    }

    public void setTags(ArrayList<String> pTags){
        for(int i=0; i< pTags.size(); i++){
            for(int j=0; j< tags.size(); j++){
                if(tags.contains(pTags.get(i).toLowerCase())){
                    continue;
                }else{
                    tags.add(pTags.get(i));
                    break;
                }
            }
        }
    }

    public void addTag(String pTag) throws BitLockerException{
        if(tags.contains(pTag.toLowerCase())){
            throw new BitLockerException("Le tag "+pTag+ " existe déjas pour ce compte");
        }else{
            tags.add(pTag);
        }
    }

    public void delTag(String pTag) throws BitLockerException{
        if(tags.contains(pTag)){
            tags.remove(pTag);
        }else{
            throw new BitLockerException("Le tag '"+pTag +"' n'est pas présent. vérifiez l'orthographe" );
        }
    }

    public void delAlltags() {
        tags.removeAll(tags); 
    }

    public void setIdcs(ArrayList<Idc> pIdcs){
        idcs = pIdcs;
    }

    public String toString(){
        return "Account: " + super.toString() + " " + id + " " + idcs + " " + tags;
    }
}