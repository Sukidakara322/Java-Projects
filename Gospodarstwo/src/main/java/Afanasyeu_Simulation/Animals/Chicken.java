package Afanasyeu_Simulation.Animals;
import Afanasyeu_Simulation.Animal;

import java.util.Random;

/**
 * Jest to konstruktor dla kurczków, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdego kurczaka
 */
public class Chicken extends Animal {
    /**
     * eggsProduced - zmienna do ustawienia ilości jajek wyprodukowanego w ciągu jednego dnia
     */
    private Integer eggsProduced;
    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania kurczaka
     */
    private Double dailyCost;
    /**
     * delivery - zmienna do ustalenia kosztu dziennej dostawy produktów wyprodukowanych przez krowę
     */
    private Double delivery;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla kurczaków
     * @param age parametr zawierający informację o wieku kurczaka
     */

    public Chicken(Integer age) {
        super(age);
        eggsProduced = 0;
        dailyCost = 2.0;
        delivery = 1.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli kurczaków
     * @param random parametr do działania hodowli
     * @return zwraca współczynnik hodowli dla kurczaków
     */
    @Override
    public boolean reproduce(Random random) {
        Double reproductionChance = 0.7;
        return random.nextDouble() < reproductionChance;
    }

    /**
     * Metoda sprawdzania płci nowonarodzonego zwierzęcia
     * @param random parametr dla wylosowania zwierzęcia
     * @return zwraca współczynnik określający prawdopodobieństwo urodzenia samca
     */
    @Override
    public boolean isMale(Random random) {
        Double maleChance = 0.4;
        return random.nextDouble() < maleChance;
    }

    /**
     * Metoda sprawdzania gotowości kurczaka do dawania jajek
     */
    public void layEgg() {
        if(age >= 1){
            Integer eggsPerDay = 1;
            eggsProduced += eggsPerDay;
        }
        else if (age < 1){
            Integer eggsPerDay = 0;
            eggsProduced += eggsPerDay;
        }
    }

    /**
     * Metoda obliczania zysku dla kurczaka
     * @return zwraca zysk za rok dla kurczaka
     */
    @Override
    public Double calculateProfit() {
        Double eggPrice = 4.5;
        return 365*(eggsProduced * eggPrice - dailyCost - delivery);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku kurczaków
     * @return zwraca maksymalny wiek kurczaków
     */
    @Override
    public Integer getMaxAge() {
        return 6;
    }
}
