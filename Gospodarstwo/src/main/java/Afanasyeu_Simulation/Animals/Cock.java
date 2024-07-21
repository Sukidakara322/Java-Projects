package Afanasyeu_Simulation.Animals;

import Afanasyeu_Simulation.Animal;

import java.util.Random;

/**
 * Jest to konstruktor dla kogutów, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdego kogutu
 */
public class Cock extends Animal {

    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania kogutu
     */
    private Double dailyCost;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla kogutów
     * @param age parametr zawierający informację o wieku kogutu
     */
    public Cock(Integer age) {
        super(age);
        dailyCost = 1.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli kogutów
     * @param random parametr do działania hodowli
     * @return zwraca informacje o niemożliwości hodowli dla kogutów
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
     * Metoda obliczania zysku dla kogutów
     * @return zwraca zysk za rok dla kogutów
     */

    @Override
    public Double calculateProfit() {
        return (-365*dailyCost);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku kogutów
     * @return zwraca maksymalny wiek kogutów
     */
    @Override
    public Integer getMaxAge() {
        return 9;
    }
}

