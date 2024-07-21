import Afanasyeu_Simulation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * Jest to główna klasa 'Main', gdzie wprowadzane są wszystkie wartości wejściowe,
 * wywoływane są wyniki symulacji z klasy 'Farm' oraz wypisywane są zmiany farmy w czasie i wyniki symulacji.
 */
public class Main {
    /**
     * To jest punkt wyjścia symulacji
     * @param args Wartości danych wiersza poleceń
     */
    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Simulation of the farm - Gospodarstwo - Aliaksandr Afanasyeu, Aliaksei Papianiuk - Grupa 12, PT 13:15");
        System.out.println("-----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);

        //deaths - zmienna typu integer przechowująca liczbę martwych zwierząt ze starości
        //deaths_from_illness - zmienna typu integer, która przechowuje liczbę zwierząt, które padły z powodu chorób
        Integer deaths = 0;
        Integer deaths_from_illness = 0;

        System.out.print("Please enter the amount of cows: ");
        //cowCount - zmienna typu integer do przechowywania liczby pierwotnie zakupionych krów
        Integer cowCount = scanner.nextInt();

        System.out.print("Please enter the amount of chicken: ");
        //chickenCount - zmienna typu integer do przechowywania liczby pierwotnie zakupionych kurczaków
        Integer chickenCount = scanner.nextInt();

        System.out.print("Please enter the amount of sheep: ");
        //sheepCount - zmienna typu integer do przechowywania liczby pierwotnie zakupionych owiec
        Integer sheepCount = scanner.nextInt();

        System.out.print("Please enter the time for simulation(in years): ");
        //Time - zmienna typu integer do przechowywania liczby lat do symulacji
        Integer Time = scanner.nextInt();

        //farmSimulation - zmienna typu Farm do przechowywania danych o symulacji i użycia wszystkich metodów
        Farm farmSimulation = new Farm();

        //Inicjalizacja farmy
        farmSimulation.initializeFarm(cowCount, chickenCount, sheepCount);

        Random random = new Random();


        //Tworzenie zmiennej typu 'List' do przechowywania danych o zyskach gospodarstwa w poszczególnych latach
        List<Double> yearlyProfits = new ArrayList<>();
        Double totalProfit = 0.0;

        //Pętla, która wywołuje wszystkie metody z klasy 'Farm' i stosuje ich dla każdego roku
        for (int year = 1; year <= Time; year++) {
            System.out.println("-----------------------------------------------------------------------------------------------------");
            System.out.print("Year number " + year + ":\n");
            farmSimulation.incrementAnimalAges();
            farmSimulation.removeAnimals(random);
            farmSimulation.removeIllAnimals(random);
            farmSimulation.produceAnimalProducts();
            farmSimulation.reproduceAnimals(random);

            Double profit = farmSimulation.calculateProfit();
            yearlyProfits.add(profit);
            totalProfit += profit;

            System.out.println("Profit: " + profit);
            System.out.println("Sum of dead animals: " + farmSimulation.getDeadAnimalCount() + " (Dead cows: " + farmSimulation.getDeadCowCount() +
                    ", Dead chicken: " + farmSimulation.getDeadChickenCount() + ", Dead sheep: " + farmSimulation.getDeadSheepCount() + ", Dead bulls: " + farmSimulation.getDeadBullCount()
                    + ", Dead cocks: " + farmSimulation.getDeadCockCount() + ", Dead rams: " + farmSimulation.getDeadRamCount() + " )");
            System.out.println("Sum of dead from illness animals: " + farmSimulation.getDeadIllAnimalCount() + " (Dead cows: " + farmSimulation.getDeadIllCowCount() +
                    ", Dead chicken: " + farmSimulation.getDeadIllChickenCount() + ", Dead sheep: " + farmSimulation.getDeadIllSheepCount() + ", Dead bulls: " + farmSimulation.getDeadIllBullCount()
                    + ", Dead cocks: " + farmSimulation.getDeadIllCockCount() + ", Dead rams: " + farmSimulation.getDeadIllRamCount() + " )");
            System.out.println("Actual amount of cows: " + farmSimulation.getCowCount());
            System.out.println("Actual amount of chicken: " + farmSimulation.getChickenCount());
            System.out.println("Actual amount of sheep: " + farmSimulation.getSheepCount());
            System.out.println("Actual amount of bulls: " + farmSimulation.getBullCount());
            System.out.println("Actual amount of cocks: " + farmSimulation.getCockCount());
            System.out.println("Actual amount of rams: " + farmSimulation.getRamCount());

            deaths = farmSimulation.getDeadAnimalCount();
            deaths_from_illness = farmSimulation.getDeadIllAnimalCount();
        }

        //Wyjście podsumowania symulacji
        System.out.println("-----------------------------------------------------------------------------------------------------");
        System.out.println("Results:");
        System.out.println("\nWhole time profit: " + totalProfit);

        System.out.println("Total amount of dead animals: " + deaths);
        System.out.println("Total amount of dead from illness animals: " + deaths_from_illness);

        Integer farmCowCount = farmSimulation.getCowCount();
        Integer farmChickenCount = farmSimulation.getChickenCount();
        Integer farmSheepCount = farmSimulation.getSheepCount();
        Integer farmBullCount = farmSimulation.getBullCount();
        Integer farmCockCount = farmSimulation.getCockCount();
        Integer farmRamCount = farmSimulation.getRamCount();
        System.out.println("\nFinal amount of animals of each type:");
        System.out.println("Cows: " + farmCowCount);
        System.out.println("Chicken: " + farmChickenCount);
        System.out.println("Sheep: " + farmSheepCount);
        System.out.println("Bulls: " + farmBullCount);
        System.out.println("Cocks: " + farmCockCount);
        System.out.println("Rams: " + farmRamCount);
    }
}
