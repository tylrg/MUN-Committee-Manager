import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.*;
import javafx.geometry.Rectangle2D;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import java.util.*;
import cs1.*;
public class Committee extends Application
{
    private int quorum;
    private ArrayList<Delegate> members;
    
    private ArrayList<Delegate> speakersList;
    private TableView table;

    public ArrayList<Delegate> getMembers()
    {
        return members;
    }

    public static void main(String[]args)
    {
        launch(args);
    }

    public Committee(ArrayList<Delegate> list)
    {
        members=list;
        quorum= list.size();
    }

    public static void setTime()

    {

        Calendar cal = Calendar.getInstance();

        System.out.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(cal.getTime()));

    }

    public static void getTimerTime()

    {

        Calendar cal = Calendar.getInstance();

        System.out.println(new SimpleDateFormat("HH:mm:ss").format(cal.getTime()));

    }

    public String getTime()

    {

        Calendar cal = Calendar.getInstance();

        return (new SimpleDateFormat("MM/dd/yyyy").format(cal.getTime()));

    }

    public void setSpeakersList(ArrayList<Delegate> d){
        speakersList= d;
    }

    public ArrayList<Delegate> getSpeakers()
    {
        return speakersList;
    }

    

    public int getQuorum(){
        return quorum;
    }

    public int getTwoThirds()

    { 

        if(quorum%3==0)

        {

            return (int)(quorum*(2/(double)3));

        }

        return (int)(quorum*((double)2/3))+1;

    }

    public int getSimpleMajority(){
        return quorum/2+1;
    }

    public void spkRun()
    {
        System.out.print("What do you want the speaking time to be? (sec): ");
        String dumb = "";
        int speak = Keyboard.readInt();
        System.out.print("How many extensions do you want? ");
        int numExt = Keyboard.readInt();
        boolean exten = false;
        int ext = 0;
        if (numExt != 0)
        {
            System.out.print("How long is each extension? (sec) ");
            ext = Keyboard.readInt();
            exten = true;
        }
        System.out.print("\f");
        ArrayList<Delegate> curr = speakersList;

        Clock timer = new Clock(speak);
        String time=timer.displayTimerTime();
        boolean out = true;
        for (int x = 0; x < curr.size(); x++)
        {
            int currNum=numExt;
            while((currNum>=0)&&(out))
            {

                if (currNum==numExt)
                {
                    if (x > 0)
                    {
                        System.out.println("\fEnd of speaker\n");
                    }
                    System.out.println("Type any number to start the next speaker or type 'exit' to leave the speaker's list");
                    if (Keyboard.readString().equals("exit"))
                    {
                        out=false;
                    }
                    timer = new Clock(speak);
                }
                while ((timer.getTotalSeconds()!=0) && (out))
                {

                    timer.decSecond();
                    System.out.print("\f");
                    System.out.print("Time remaining: " + timer.displayTimerTime());
                    if (exten)
                    {
                        System.out.println("\nExtensions left: " + currNum + " x " + ext + "sec");
                    }
                    time=timer.displayTimerTime();
                    try
                    {   Thread.sleep(1000);  }
                    catch(InterruptedException ie)
                    {}
                }
                if ((currNum!=0) && (out))
                {
                    System.out.println("Do you want to use an extension? (y/n) ");
                    dumb = Keyboard.readString();
                    while (!(dumb.equals("y")) && !(dumb.equals("n")))
                    {
                        System.out.print("Please type in either 'y' or 'n' for yes or no");
                        dumb = Keyboard.readString();
                    }
                    if (dumb.equals("y"))
                    {
                        currNum--;
                    }
                    else
                    {
                        currNum = -1;
                    }
                }
                else
                {
                    currNum--;
                }
                timer = new Clock(ext);

            }
        }
        System.out.print("\fThe Speaker's List has ended!");
        try

        {   Thread.sleep(3000);  }

        catch(InterruptedException ie)

        {}
        System.out.print("\f");
    }

    public  void start(Stage primary)
    {

    }

    public  void showMembers()
    {
        Stage window= new Stage();

        window.setTitle("Committee Members");
        TableColumn<Delegate,String> nameColumn= new TableColumn<>("Nation");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
        TableView table= new TableView<>();
        table.setItems(displayList(members));
        table.getColumns().addAll(nameColumn);
        Scene spkScene= new Scene(table,500,500);
        window.setScene(spkScene);
        window.show();

    }

    public ObservableList<Delegate> displayList(ArrayList<Delegate> d)

    {
        ObservableList<Delegate> base= FXCollections.observableArrayList();
        for(Delegate del: d)
        {
            base.add(del);
        }
        return base;

    }

    public void deleteButton()
    {
        ObservableList<Delegate> delegateSelected, allDelegates;
        allDelegates= table.getItems();
        delegateSelected= table.getSelectionModel().getSelectedItems();
        delegateSelected.forEach(allDelegates:: remove);

    }

    public void showSpeakers(Stage primary, Committee mainComm)
    {
        if(speakersList!=null)
        {
            Stage window= new Stage();

            window.setTitle("Speaker's List");
            VBox cooler= new VBox();
            HBox coolest= new HBox();
            Label intr= new Label("Click here to remove a delegate from the table");
            TableColumn<Delegate,String> nameColumn= new TableColumn<>("Speaker's List");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("Country"));
            table= new TableView<>();
            Button remove= new Button("Remove");
            Button start = new Button("Start the Speaker's List");
            coolest.getChildren().addAll(intr,remove,start);
            coolest.setSpacing(10);

            table.setItems(displayList(speakersList));
            table.getColumns().addAll(nameColumn);
            cooler.getChildren().addAll(table,coolest);
            Scene spkScene= new Scene(cooler,500,450);
            remove.setOnAction(e->deleteButton());
            start.setOnAction(e->mainComm.spkRun());
            window.setScene(spkScene);
            window.show();
        }
        else
        {
            System.err.print("Speaker's List is empty");
            try

            {   Thread.sleep(3000);  }

            catch(InterruptedException ie)

            {}
            System.err.print("\f");
        }
    }

}
