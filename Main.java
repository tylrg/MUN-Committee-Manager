
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

import javafx.scene.control.TableView;

import javafx.collections.FXCollections;

import javafx.collections.ObservableList;

import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import java.util.*;
import cs1.*;

public class Main extends Application

{

    public static void main(String [] args)

    {

        launch(args);

    }

    public void start(Stage window) throws Exception
    {
        window.setTitle("MUN Committee Manager");   //WELCOME CODE
        Button hi= new Button(" Welcome to MUN Committee Manager ");/////////////////////////////WELCOME CODE
        StackPane wLayout= new StackPane();
        wLayout.getChildren().addAll(hi);
        Scene wscene = new Scene (wLayout,500,500);//DISPLAY CODE
        window.setScene(wscene);
        window.show();
        hi.setOnAction(e->promptInput(window));//////////////TO INPUT
    }

    public static void commRun(Stage window,Committee mainComm)

    {
        //MASTERTIME m= new MASTERTIME();
        //m.start();

        Label date= new Label("Date: "+mainComm.getTime());//same ''
        VBox timeDisplay= new VBox();////////////////////////////////////////////TIME AND DATE
        timeDisplay.getChildren().addAll(date);
        VBox stats= new VBox();
        Label quorum= new Label("Quorum:"+mainComm.getQuorum());// +committee.getQuorum ();
        Label twothree= new Label("2/3:"+mainComm.getTwoThirds());// +committee.getTwoThirds();
        Label major= new Label("Simple Majority:"+mainComm.getSimpleMajority()); //+ committee.getMajority();
        stats.getChildren().addAll(quorum,twothree,major);///////////////////////////////////Statistics
        BorderPane mainPane= new BorderPane();
        mainPane.setTop(timeDisplay);
        mainPane.setBottom(stats);
        VBox options= new VBox();
        Button why= new Button("Show Speaker's List");
        Button membs= new Button("Show Committee Members");
        Button sl= new Button("Begin speaker's list");

        Button caucusButton= new Button("Begin caucus");

        options.getChildren().addAll(sl,caucusButton,why,membs);
        options.setSpacing(10);
        options.setPadding(new Insets(50,50,50,50));

        mainPane.setCenter(options);

        Scene mainScene= new Scene(mainPane,500,500);

        caucusButton.setOnAction(e->caucusPrompt(window,mainComm));
        membs.setOnAction(e->mainComm.showMembers());
        why.setOnAction(e->mainComm.showSpeakers(window,mainComm));
        sl.setOnAction(e->spkPrompt(window,mainComm));

        window.setScene(mainScene);

        window.show();

    }

    public static void promptInput(Stage window)
    {
        VBox inputBox= new VBox();
        inputBox.setPadding(new Insets(20,20,20,20));
        Label delPrompt= new Label("Please enter the names of the countries present in committee");
        TextField addDel= new TextField("Chad,Nepal,Japan,New Zealand,");///////REMEMBER TO ADD eg.before final
        Button done= new Button("Done");
        inputBox.getChildren().addAll(delPrompt,addDel,done);///////////////////////Input Code
        Scene inputScene= new Scene(inputBox,750,250);
        Committee mainComm;
        done.setOnAction(e->commRun(window,startComm(addDel.getText())));////////////////////////Text Reader
        window.setScene(inputScene);
        window.show();
    }

    public static Committee startComm(String s)

    {

        ArrayList<Delegate> base= new ArrayList();

        boolean fin=true;

        while(fin)

        {

            if((s.indexOf(",")!=-1)&&(s.indexOf(",")!=0))// commas are present but not first

            {

                if(s.indexOf(",")!=s.length()-1)///not the last comma

                {

                    base.add(new Delegate(s.substring(0,s.indexOf(","))));

                    s=s.substring(s.indexOf(",")+1);

                }

                else

                {

                    base.add(new Delegate(s.substring(0,s.indexOf(","))));

                    fin=false;

                }

            }

            else

            {

                System.err.println("Invalid input format! Please end every delegate entry with a comma!");

                break;

            }

        }

        Committee mainComm= new Committee(base);

        return mainComm;

    }

    public static void caucusPrompt(Stage window,Committee mainComm)
    {
        BorderPane caucusPane= new BorderPane();

        Label caucusQ= new Label("What type of caucus would you like to start?");

        VBox format= new VBox();

        HBox options= new HBox();

        Button unmod= new Button("Unmoderated Caucus");

        Button mod= new Button("Moderated Caucus");

        options.getChildren().addAll(mod,unmod);
        format.getChildren().addAll(caucusQ,options);
        format.setSpacing(10);
        caucusPane.setCenter(format);
        mod.setOnAction(e->runModCaucus(window,mainComm));
        unmod.setOnAction(e->unMod(window,mainComm));

        Scene caucusScene= new Scene(caucusPane,500,500);
        window.setScene(caucusScene);////////////////////Display stuff
        window.show();
    }

    public static void spkPrompt(Stage window, Committee mainComm)
    {
        mainComm.showMembers();
        BorderPane spkPane= new BorderPane();
        Label warn= new Label("Please refer to the member list while creating a speaker's list!");
        VBox format= new VBox();
        HBox cool= new HBox();
        Label instr= new Label("Please enter delegates to be added to the Speaker's List");
        TextField addSpk= new TextField("Chad,Nepal,Japan,New Zealand,");///////////////prompts
        Button add= new Button("Add Delegates");
        Button done= new Button("Done");

        instr.setPadding(new Insets(0,0,10,0));

        addSpk.setPadding(new Insets(0,10,0,0));/////////formatting

        cool.getChildren().addAll(add,done);
        format.getChildren().addAll(warn,addSpk,instr);
        format.setSpacing(10);
        //add.setOnAction(e->mainComm.start(window));
        spkPane.setTop(format);
        spkPane.setCenter(cool);

        Scene spkScene= new Scene(spkPane,500,500);
        window.setScene(spkScene);
        window.show();

        add.setOnAction(e->mainComm.setSpeakersList(startSpk(window,addSpk.getText(),mainComm)));
        
        done.setOnAction(e->commRun(window,mainComm));
    }
    public static ArrayList<Delegate> startSpk(Stage window,String s,Committee mainComm)
    {
        ArrayList<Delegate> base= new ArrayList();
        boolean fin=true;
        while(fin)
        {
            if((s.indexOf(",")!=-1)&&(s.indexOf(",")!=0))// commas are present but not first
            {
                if(s.indexOf(",")!=s.length()-1)///not the last comma
                {

                    base.add(new Delegate(s.substring(0,s.indexOf(","))));
                    s=s.substring(s.indexOf(",")+1);

                }
                else
                {

                    base.add(new Delegate(s.substring(0,s.indexOf(","))));
                    fin=false;

                }
            }
            else
            {
                System.err.println("Invalid input format! Please end every delegate entry with a comma!");
                break;
            }
        }
        return base;

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

    public static void runModCaucus(Stage window, Committee mainComm)
    {
        boolean isClockRunning = true;
        System.out.print("Total time of caucus(min)(for 30 seconds, put '.5'):  ");
        double total = (Keyboard.readDouble()*60);
        System.out.print("Speaking Time(sec):  ");
        double speak = Keyboard.readDouble();
        while (total % speak != 0)
        {
            System.out.println("That speaking time/time of caucus combination does not work!");
            System.out.print("Which would you like to change?(total) or (speak): "); //This is where a button would be on the GUI
            if (Keyboard.readString().toLowerCase().equals("total"))
            {
                System.out.print("What should the new total time for the caucus be?  ");
                total = Keyboard.readDouble();
            }
            else//SUGGEST SPEAKING TIME
            {
                System.out.print("What should the new speaking time be?  ");
                speak = Keyboard.readDouble();
            }
        }
        int stopTime = (int)total-(int)speak;
        Clock timer = new Clock((int)total);
        System.out.println("Type any number to start the clock");
        int nonsense = Keyboard.readInt();
        String time=timer.displayTimerTime();
        boolean out = true;
        while(!time.equals("0:00")&&(out))
        {
            while(timer.getTotalSeconds()!=stopTime)
            {
                timer.decSecond();
                System.out.print("\f");
                System.out.print(timer.displayTimerTime());
                time=timer.displayTimerTime();
                try
                {   Thread.sleep(1000);  }
                catch(InterruptedException ie)
                {}
            }
            if(!time.equals("0:00"))
            {
                System.out.println("\nType any number to start the next speaker or type 'stop' to stop the caucus");
                if (Keyboard.readString().toLowerCase().equals("stop"))
                {
                    out=false;
                }
                stopTime-=speak;
            }
        }
        System.out.println("\fThis moderated caucus has expired!");
        try
        {   Thread.sleep(3000);  }
        catch(InterruptedException ie)
        {}
        System.out.println("\f");
        commRun(window,mainComm);
    }

    public static void unMod(Stage window, Committee mainComm)
    {
        System.out.println("How long is the unmoderated caucus?(min) ");
        Clock timer = new Clock(Keyboard.readInt()*60);
        while (timer.getTotalSeconds()!=0)
        {
            timer.decSecond();
            System.out.print("\f");
            System.out.print("Time remaining: " + timer.displayTimerTime());
            try
            {   Thread.sleep(1000);  }
            catch(InterruptedException ie)
            {}
        }
        System.out.println("\fThis unmoderated caucus has expired!");
        try
        {   Thread.sleep(3000);  }
        catch(InterruptedException ie)
        {}
        System.out.println("\f");
        commRun(window,mainComm);
    }
}

