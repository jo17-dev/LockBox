/*
 * class for manage Idc
 * "Identifiant de connexion" and belong to ONE account
 */

public class Idc extends BasicComponent {
    private String value; // value of the idc

    public Idc(String label, String value){
        super(label);
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public void setValue(String pValue){
        value = pValue;
    }

    public String toString(){
        return "Idc: " + super.toString() + " " + value;
    }
}
