import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import users.User;
import users.UsersController;

public class UsersControllerTest {
    
    public UsersControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    //metoda addNewOnlineUser(Socket socket, String username) koja dodaje novog korisnika na listu online korisnika datog programa
    //metoda vraca potvrdan odgovor u slucaju da korisnicko ime nije vec prijavljeno na sistem, te negativan u slucaju da je korisnicko ime vec zauzeto
    @Test
    public void addNewOnlineUserTest(){
        UsersController controller = new UsersController();
        controller.setTestFlag(true);
        
        //prijavljujemo korisnika na sistem sa validnim imenom
        boolean expectedResult = true;
        boolean result = controller.addNewOnlineUser(null, "Alan");
        assertEquals(expectedResult, result);
        
        //prijavljujemo korisnika na sistem sa nevalidnim imenom
        boolean expectedResultTwo = false;
        boolean resultTwo = controller.addNewOnlineUser(null, "Alan");
        assertEquals(expectedResultTwo, resultTwo);
        
        
    }
    
    //metoda isUsernameAvailable(String username) provjerava da li proslijedjeni String argument predstavlja nezauzeto korisnicko ime
    //metoda vraca pozitivan boolean odgovor u slucaju nezauzeca korisnickog imena, te negativan u suprotnom
    @Test
    public void isUsernameAvailableTest(){
        UsersController controller = new UsersController();
        controller.setTestFlag(true);
        
        //prijavimo nekoliko korisnika na sistem
        controller.addNewOnlineUser(null, "John");
        controller.addNewOnlineUser(null, "Nataly");
        controller.addNewOnlineUser(null, "Jack");
        
        //provjera u slucaju nezauzetog korisnickog imena
        boolean expectedResult = true;
        boolean result = controller.isUsernameAvailable("Alan");
        assertEquals(expectedResult, result);
        
        //provjera u slucaju zauzetog korisnikog imena
        boolean expectedResultTwo = false;
        boolean resultTwo = controller.isUsernameAvailable("Nataly");
        assertEquals(expectedResultTwo, resultTwo);
    }
    
    //metoda removeUser(String username) provjerava da li proslijedjeni String predstavlja korisnicko ime nekog prijavljenog korisnika
    //u slucaju da je proslijedjeni parametar ime nekog prijavljenog korisnika, metoda brise istog sa liste prijavljenih te vraca potvrdan odgovor
    //u slucaju da proslijedjeni parametar nije ime nekog prijavljenog korisnika, metoda vraca negativa boolean odgovor
    @Test
    public void removeUserTest(){
        UsersController controller = new UsersController();
        controller.setTestFlag(true);
        
        //prijavimo nekoliko korisnika na sistem
        controller.addNewOnlineUser(null, "John");
        controller.addNewOnlineUser(null, "Nataly");
        controller.addNewOnlineUser(null, "Jack");
        
        //provjera u slucaju da nema prijavljenih korisnika sa tim korisnickim imenom
        boolean expectedResult = false;
        boolean result = controller.removeUser("Alan");
        assertEquals(expectedResult, result);
        
        //provjera u slucaju da postoji prijavljen korisnik sa tim korisnickim imenom
        boolean expectedResultTwo = true;
        boolean resultTwo = controller.removeUser("Nataly");
        assertEquals(expectedResultTwo, resultTwo);
    }
    
    //metoda getUserFromListForString(String username) vraca referencu na User objekat ukoliko pronadje korisnika sa proslijedjenim korisnickim imenom
    //u slucaju da nije moguce pronaci korisnika sa proslijedjenim korisnickim imenom, metoda vraca null
    @Test
    public void getUserFromListForStringTest(){
        UsersController controller = new UsersController();
        controller.setTestFlag(true);
        
        //prijavimo nekoliko korisnika na sistem
        controller.addNewOnlineUser(null, "John");
        controller.addNewOnlineUser(null, "Nataly");
        controller.addNewOnlineUser(null, "Jack");
        
        //provjera u slucaju da nema prijavljenih korisnika sa tim korisnickim imenom
        User result = controller.getUserFromListForString("Alan");
        assertNull(result);
        
        //provjera u slucaju da postoji prijavljen korisnik sa tim korisnickim imenom
        User resultTwo = controller.getUserFromListForString("John");
        assertNotNull(resultTwo);
    }
}
