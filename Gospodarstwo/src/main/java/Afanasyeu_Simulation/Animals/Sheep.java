package Afanasyeu_Simulation.Animals;

import Afanasyeu_Simulation.Animal;

import java.util.Random;

/**
 * Jest to konstruktor dla owiec, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdej owcy
 */
public class Sheep extends Animal {
    /**
     * woolProduced - zmienna do ustawienia ilości wełna wyprodukowanego w ciągu jednego roku
     */
    private Double woolProduced;
    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania owcy
     */
    private Double dailyCost;
    /**
     * delivery - zmienna do ustalenia kosztu dziennej dostawy produktów wyprodukowanych przez owcę
     */
    private Double delivery;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla owiec
     * @param age parametr zawierający informację o wieku owcy
     */
    public Sheep(Integer age) {
        super(age);
        woolProduced = 0.0;
        dailyCost = 1.5;
        delivery = 1.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli owiec
     * @param random parametr do działania hodowli
     * @return zwraca współczynnik hodowli dla owiec
     */

    @Override
    public boolean reproduce(Random random) {
        Double reproductionChance = 0.6;
        return random.nextDouble() < reproductionChance;
    }

    /**
     * Metoda sprawdzania płci nowonarodzonego zwierzęcia
     * @param random parametr dla wylosowania zwierzęcia
     * @return zwraca współczynnik określający prawdopodobieństwo urodzenia samca
     */
    @Override
    public boolean isMale(Random random) {
        Double maleChance = 0.35;
        return random.nextDouble() < maleChance;
    }

    /**
     * Metoda sprawdzania gotowości owcy do dawania jajek
     */
    public void produceWool() {
        if(age >= 3){
            Double woolPerYear = 14.0;
            woolProduced += woolPerYear;
        }
        else if(age < 3){
            Double woolPerYear = 0.0;
            woolProduced += woolPerYear;
        }
    }

    /**
     * Metoda obliczania zysku dla owiec
     * @return zwraca zysk za rok dla owcy
     */
    @Override
    public Double calculateProfit() {
        Double woolPrice = 40.0;
        return woolProduced * woolPrice - 365*(dailyCost + delivery);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku owiec
     * @return zwraca maksymalny wiek owiec
     */
    @Override
    public Integer getMaxAge() {
        return 15;
    }
}
