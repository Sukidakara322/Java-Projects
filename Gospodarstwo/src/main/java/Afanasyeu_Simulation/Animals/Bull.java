package Afanasyeu_Simulation.Animals;

import Afanasyeu_Simulation.Animal;

import java.util.Random;

/**
 * Jest to konstruktor dla buków, w którym wszystkie wartości są ustawione
 * na konkretne komórki i użyte w klasie abstrakcyjnej 'Animal',
 * w tej klasie ustawiane są również wszystkie parametry dla każdego byku
 */
public class Bull extends Animal {
    /**
     * dailyCost - zmienna służąca do ustalania kosztu dziennego utrzymania byku
     */
    private Double dailyCost;

    /**
     * Konstruktor do ustawiania początkowych parametrów statycznych dla byków
     * @param age parametr zawierający informację o wieku byku
     */
    public Bull(Integer age) {
        super(age);
        dailyCost = 4.0;
    }

    /**
     * Metoda do ustalania parametrów hodowli byków
     * @param random parametr do działania hodowli
     * @return zwraca informacje o niemożliwości hodowli dla byków
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
     * Metoda obliczania zysku dla byków
     * @return zwraca zysk za rok dla byku
     */
    @Override
    public Double calculateProfit() {
        return (-365*dailyCost);
    }

    /**
     * Metoda uzyskiwania maksymalnego wieku byków
     * @return zwraca maksymalny wiek byków
     */
    @Override
    public Integer getMaxAge() {
        return 20;
    }
}
