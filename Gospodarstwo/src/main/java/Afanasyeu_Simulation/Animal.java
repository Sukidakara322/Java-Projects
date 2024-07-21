package Afanasyeu_Simulation;
import java.util.Random;

/**
 * Jest to klasa abstrakcyjna 'Animal' do pracy ze zwierzętami
 * każdego typu według szablonu z klas dla każdego typu zwierząt
 */
public abstract class Animal {
    /**
     * age - zmienna typu integer do przechowywania danych o wieku zwierząt
     */
    protected Integer age;
    /**
     * profit - zmienna typu double do przechowywania danych o zyskach każdego typu zwierząt
     */
    private Double profit;

    /**
     * Konstruktor do pracy z wiekiem
     * @param age parametr wieku przypisany każdemu zwierzęciu
     */
    public Animal(Integer age) {
        this.age = age;
    }

    /**
     * Konstruktor do zwiększenia wieku zwięrząt
     */
    public void incrementAge() {
        age++;
    }

    /**
     * Konstructor do zwracania wieku każdego zwierzęcia
     * @return zwraca wiek zwierzęcia
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Układ konstruktora hodowli
     * @param random parametr do działania hodowli
     * @return zwraca prawdopodobieństwo hodowli dla każdego typu zwierzęcia
     */
    public abstract boolean reproduce(Random random);

    /**
     * Układ konstruktora kontrole płci
     * @param random parametr dla wylosowania zwierzęcia
     * @return zwraca tak dla samców i nie dla samic
     */
    public abstract boolean isMale(Random random);

    /**
     * Układ konstruktora obliczania zysku
     * @return zwraca zysk na zwierzęciu
     */
    public abstract Double calculateProfit();

    /**
     * Układ konstruktora zwracania maksymalnego wieku każdego typu zwierzęcia
     * @return zwraca maksymalny wiek zwierzęcia
     */
    public abstract Integer getMaxAge();
}



