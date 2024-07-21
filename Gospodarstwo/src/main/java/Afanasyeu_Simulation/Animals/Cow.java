package Afanasyeu_Simulation.Animals;

import Afanasyeu_Simulation.Animal;

import java.util.Random;

/**
 * Jest to konstruktor dla krów, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdej krowy
 */
public class Cow extends Animal {
    /**
     * milkProduced - zmienna do ustawienia ilości mleka wyprodukowanego w ciągu jednego dnia
     */
    private Double milkProduced;
    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania krowy
     */
    private Double dailyCost;
    /**
     * delivery - zmienna do ustalenia kosztu dziennej dostawy produktów wyprodukowanych przez krowę
     */
    private Double delivery;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla krów
     * @param age parametr zawierający informację o wieku krowy
     */
    public Cow(Integer age) {
        super(age);
        milkProduced = 0.0;
        dailyCost = 4.0;
        delivery = 1.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli krów
     * @param random parametr do działania hodowli
     * @return zwraca współczynnik hodowli dla krów
     */
    @Override
    public boolean reproduce(Random random) {
        Double reproductionChance = 0.5;
        return random.nextDouble() < reproductionChance;
    }

    /**
     * Metoda sprawdzania płci nowonarodzonego zwierzęcia
     * @param random parametr dla wylosowania zwierzęcia
     * @return zwraca współczynnik określający prawdopodobieństwo urodzenia samca
     */
    @Override
    public boolean isMale(Random random) {
        Double maleChance = 0.3;
        return random.nextDouble() < maleChance;
    }

    /**
     * Metoda sprawdzania gotowości krowy do dawania mleka
     */
    public void produceMilk() {
        if(age >= 2){
            Double milkPerDay = 8.0;
            milkProduced += milkPerDay;
        }
        else if(age < 2){
            Double milkPerDay = 0.0;
            milkProduced += milkPerDay;
        }
    }

    /**
     * Metoda obliczania zysku dla krówy
     * @return zwraca zysk za rok dla krówy
     */
    @Override
    public Double calculateProfit() {
        Double milkPrice = 4.0;
        return 365*(milkProduced * milkPrice - dailyCost - delivery);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku krów
     * @return zwraca maksymalny wiek krów
     */
    @Override
    public Integer getMaxAge() {
        return 18;
    }
}

