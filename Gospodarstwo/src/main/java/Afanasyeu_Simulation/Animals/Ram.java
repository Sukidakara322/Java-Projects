package Afanasyeu_Simulation.Animals;

import Afanasyeu_Simulation.Animal;

import java.util.Random;
/**
 * Jest to konstruktor dla baranów, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdego baranu
 */
public class Ram extends Animal {
    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania baranu
     */
    private Double dailyCost;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla baranów
     * @param age parametr zawierający informację o wieku baranu
     */
    public Ram(Integer age) {
        super(age);
        dailyCost = 2.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli baranów
     * @param random parametr do działania hodowli
     * @return zwraca informacje o niemożliwości hodowli dla baranów
     */
    @Override
    public boolean reproduce(Random random) {
        return false;
    }

    /**
     * Metoda sprawdzania płci nowonarodzonego zwierzęcia
     * @param random parametr dla wylosowania zwierzęcia
     * @return zwraca informacja o płci samca
     */
    @Override
    public boolean isMale(Random random) {
        return true;
    }

    /**
     * Metoda obliczania zysku dla baranów
     * @return zwraca zysk za rok dla baranów
     */
    @Override
    public Double calculateProfit() {
        return (-365*dailyCost);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku baranów
     * @return zwraca maksymalny wiek baranów
     */
    @Override
    public Integer getMaxAge() {
        return 16;
    }
}