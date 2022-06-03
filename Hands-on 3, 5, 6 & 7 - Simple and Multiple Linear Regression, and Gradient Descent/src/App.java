
import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

// https://youtu.be/56Tpozmoafc
public class App {
    ContainerController myContainer;
    
    public static void main(String[] args){
        App jadeApp = new App();
        jadeApp.run();
    }
    
    public void run(){
        //Create the JADE environment
        Runtime myRuntime = Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myProfile.setParameter(Profile.MAIN_HOST, "localhost");
        myProfile.setParameter(Profile.GUI, "true");
        myContainer = myRuntime.createMainContainer(myProfile);
        
        //Call the RMA GUI
        try{
            AgentController agentController = myContainer.createNewAgent("SLR", "agents.SLRAgent", null);
            agentController.start();
            agentController = myContainer.createNewAgent("MLR1", "agents.MLR1Agent", null);
            agentController.start();
            agentController = myContainer.createNewAgent("MLR2", "agents.MLR2Agent", null);
            agentController.start();
            agentController = myContainer.createNewAgent("GD", "agents.GDAgent", null);
            agentController.start();
        } catch(StaleProxyException e){
            e.printStackTrace();
        }
    }
}
