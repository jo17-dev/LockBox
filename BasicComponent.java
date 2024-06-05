import java.io.Serializable;
import java.time.LocalDate;

public class BasicComponent implements Serializable {
    private String label;
    private LocalDate createdDate;
    private LocalDate updatedDate;

    public BasicComponent(String label){
        this.label = label;
        this.createdDate = LocalDate.now();
        this.updatedDate = LocalDate.now();
    }

    // getters

    public String getLabel(){
        return label;
    }

    public LocalDate getCreatedDate(){
        return createdDate;
    }
    public LocalDate getUpdatedDate(){
        return updatedDate;
    }


    // setters part:

    public void setLabel(String pLabel){
        label = pLabel;
    }

    public void setUpdatedDate(LocalDate pUpdatedDate){
        updatedDate = pUpdatedDate;
    }

    public String toString(){
        return label + " " + createdDate.toString() + " " + updatedDate.toString();
    }
}