import activationkeys.ActivationKey;
import activationkeys.ActivationKeyController;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ActivationKeyControllerTest {
    
    public ActivationKeyControllerTest() {
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

    //metoda isKeyInList(String key) provjerava da li je proslijedjeni String argument sadrzan u listi validnih kljuceva na serveru
    //metoda vraca boolean kao odgovor validnosti datog kljuca
    @Test
    public void isKeyInListTest(){
        ActivationKeyController controller = new ActivationKeyController();
        controller.setTestFlag(true);
        //dodamo nekoliko kljuceva u listu
        controller.addActivationKey("aaaabbbbccccdddd");
        controller.addActivationKey("1111222233334444");
        controller.addActivationKey("qqqq5555wwww6666");
        
        //provjeravamo da li testirana metoda vraca pozitivan odgovor u slucaju proslijedjivanja vec dodanog kljuca
        boolean expectedResult = true;
        boolean result = controller.isKeyInList("1111222233334444");
        assertEquals(expectedResult, result);
        
        boolean expectedResultTwo = true;
        boolean resultTwo = controller.isKeyInList("qqqq5555wwww6666");
        assertEquals(expectedResultTwo, resultTwo);
        
        //provjeravamo da li testirana metoda vraca negativan odgovor u slucaju proslijedjivanja nepostojeceg kljuca
        boolean expectedResultThree = false;
        boolean resultThree = controller.isKeyInList("qwerqwerqwerqwer");
        assertEquals(expectedResultThree, resultThree);
        
        boolean expectedResultFour = false;
        boolean resultFour = controller.isKeyInList("asdf1234asdf1234");
        assertEquals(expectedResultFour, resultFour);
    }
    
    //metoda isValidKey(String key) provjerava da li je proslijedjeni String argument validan aktivacioni kljuc
    //metoda vraca boolean kao odgovor validnosti datog kljuca
    //validan aktivacioni kljuc se sastoji od 16 alfanumerickih karaktera
    @Test
    public void isValidKeyTest(){
        ActivationKeyController controller = new ActivationKeyController();
        controller.setTestFlag(true);
        
        //provjeravamo da li testirana metoda vraca pozitivan odgovor u slucaju proslijedjivanja validnog kljuca
        boolean expectedResult = true;
        boolean result = controller.isValidKey("1111222233334444");
        assertEquals(expectedResult, result);
        
        boolean expectedResultTwo = true;
        boolean resultTwo = controller.isValidKey("qqqq5555wwww6666");
        assertEquals(expectedResultTwo, resultTwo);
        
        //provjeravamo da li testirana metoda vraca negativan odgovor u slucaju proslijedjivanja nevalidnog kljuca
        
        //kljuc koji ima manje od 16 karaktera
        boolean expectedResultThree = false;
        boolean resultThree = controller.isValidKey("qwerty");
        assertEquals(expectedResultThree, resultThree);
        
        //kljuc koji ima vise od 16 karakatera
        boolean expectedResultFour = false;
        boolean resultFour = controller.isValidKey("aaaabbbbccccdddd1");
        assertEquals(expectedResultFour, resultFour);
        
        //kljuc koji sadrzi karakter koji nije alfanumericki
        boolean expectedResultFive = false;
        boolean resultFive = controller.isValidKey("aaaabbbbccccddd!");
        assertEquals(expectedResultFive, resultFive);
    }
    
    //metoda addActivationKey(String key) koja dodaje aktivacioni kljuc ukoliko je proslijedjeni String validan aktivacioni kljuc
    //metoda vraca boolean kao odgovor dodavanja datog kljuca
    //validan aktivacioni kljuc se sastoji od 16 alfanumerickih karaktera
    @Test
    public void addActivationKeyTest(){
        ActivationKeyController controller = new ActivationKeyController();
        controller.setTestFlag(true);
        
        //provjeravamo da li testirana metoda vraca pozitivan odgovor u slucaju proslijedjivanja validnog kljuca
        boolean expectedResult = true;
        boolean result = controller.addActivationKey("1111222233334444");
        assertEquals(expectedResult, result);
        
        boolean expectedResultTwo = true;
        boolean resultTwo = controller.addActivationKey("RRRR4578ssss6x66");
        assertEquals(expectedResultTwo, resultTwo);
        
        //provjeravamo da li testirana metoda vraca negativan odgovor u slucaju proslijedjivanja nevalidnog kljuca
        
        //kljuc koji ima manje od 16 karaktera
        boolean expectedResultThree = false;
        boolean resultThree = controller.addActivationKey("qwerty");
        assertEquals(expectedResultThree, resultThree);
        
        //kljuc koji ima vise od 16 karakatera
        boolean expectedResultFour = false;
        boolean resultFour = controller.addActivationKey("aaaabbbbccccdddd1");
        assertEquals(expectedResultFour, resultFour);
        
        //kljuc koji sadrzi karakter koji nije alfanumericki
        boolean expectedResultFive = false;
        boolean resultFive = controller.addActivationKey("aaaa#$%bcc&cddd!");
        assertEquals(expectedResultFive, resultFive);
    }
    
    //metoda removeActivationKey(String key) koja dodaje brise aktivacioni kljud iz liste unaprijed dodanih kljuceva.
    //Kljuc ce biti obrisan samo ako proslijedjeni String argument predstavlja vrijednost aktivacionog kljuca koji se vec nalazi u listi.
    //metoda vraca boolean kao odgovor brisanja datog kljuca
    @Test
    public void removeActivationKeyTest(){
        ActivationKeyController controller = new ActivationKeyController();
        controller.setTestFlag(true);
        
        //dodamo nekoliko kljuceva u listu
        controller.addActivationKey("aaaabbbbccccdddd");
        controller.addActivationKey("1111222233334444");
        controller.addActivationKey("qqqq5555wwww6666");
        
        //brisanje postojeceg kljuca
        boolean expectedResult = true;
        boolean result = controller.removeActivationKey("aaaabbbbccccdddd");
        assertEquals(expectedResult, result);
        
        boolean expectedResultTwo = true;
        boolean resultTwo = controller.removeActivationKey("1111222233334444");
        assertEquals(expectedResultTwo, resultTwo);
        
        //brisanje nepostojeceg kljuca
        boolean expectedResultThree = false;
        boolean resultThree = controller.removeActivationKey("qwerqwerqwerqwer");
        assertEquals(expectedResultThree, resultThree);
        
        //brisanje kljuca koji je vec obrisan
        boolean expectedResultFour = false;
        boolean resultFour = controller.removeActivationKey("aaaabbbbccccdddd");
        assertEquals(expectedResultFour, resultFour);
        
        boolean expectedResultFive = false;
        boolean resultFive = controller.removeActivationKey("aaaa222233345555");
        assertEquals(expectedResultFive, resultFive);
    }
    
    //metoda getActivationKeyForString(String key) koja vraca referencu na ActivationKey iz liste aktivacionih kljuceva ukoliko proslijedjeni
    //String odgovara nekom od kljuceva koji se nalaze u listi dodanih kljuceva
    //metoda vraca null ako nije pronadjen odgovarajuci aktivacioni kljuc ili referencu na kljuc ukoliko je on pronadjen
    @Test
    public void getActivationKeyForString(){
        ActivationKeyController controller = new ActivationKeyController();
        controller.setTestFlag(true);
        
        //dodamo nekoliko kljuceva u listu
        ActivationKey keyOne = new ActivationKey("aaaabbbbccccdddd");
        ActivationKey keyTwo = new ActivationKey("1111222233334444");
        ActivationKey keyThree = new ActivationKey("qqqq5555wwww6666");
        
        controller.addActivationKey(keyOne);
        controller.addActivationKey(keyTwo);
        controller.addActivationKey(keyThree);
        
        //u slucaju da proslijedjeni String odgovara nekom od dodanih kljuceva
        ActivationKey expectedResult = keyOne;
        ActivationKey result = controller.getActivationKeyForString("aaaabbbbccccdddd");
        assertEquals(expectedResult, result);
        
        //u slucaju da proslijedjeni String ne odgovara nekom od dodanih kljuceva
        ActivationKey expectedResultTwo = null;
        ActivationKey resultTwo = controller.getActivationKeyForString("IIIIGGGGOOOORRRR");
        assertEquals(expectedResult, result);
    }
}
