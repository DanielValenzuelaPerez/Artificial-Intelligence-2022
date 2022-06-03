
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class App {
    ContainerController myContainer;
    
    public static void main(String[] args){
        App jadeApp = new App();
        jadeApp.run();
    }
    
    public void run(){
        //Create the JADE environment
        jade.core.Runtime myRuntime = jade.core.Runtime.instance();
        Profile myProfile = new ProfileImpl();
        myProfile.setParameter(Profile.MAIN_HOST, "localhost");
        myProfile.setParameter(Profile.GUI, "true");
        myContainer = myRuntime.createMainContainer(myProfile);
        
        //Call the RMA GUI
        try{
            AgentController agentController = myContainer.createNewAgent("LR", "agent.LRAgent", null);
            agentController.start();
        } catch(StaleProxyException e){
            e.printStackTrace();
        }
    }
}
