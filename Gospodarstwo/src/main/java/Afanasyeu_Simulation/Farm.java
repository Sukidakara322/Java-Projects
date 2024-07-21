package Afanasyeu_Simulation;
import Afanasyeu_Simulation.Animals.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Jest to klasa 'Farm', która zawiera wszystkie główne metody pomyślnego działania symulacji.
 */
public class Farm {
    /**
     * animals zmienna typu 'List' zgodna z szablonem klasy abstrakcyjnej 'Animal',
     * służąca do rejestrowania zwierząt różnych typów i przechowywania informacji o każdym z nich
     */
    private List<Animal> animals;
    /**
     * yearlyProfits zmienna typu 'List' do przechowywania danych o zyskach gospodarstwa w poszczególnych latach
     */
    private List<Double> yearlyProfits;
    /**
     * totalProfit zmienna typu double do przechowywania danych o całkowitym zysku gospodarstwa
     */
    private Double totalProfit;
    /**
     * deadAnimalCount zmienna typu integer do obliczania liczby martwych zwierząt ze starości
     */
    private Integer deadAnimalCount;
    /**
     * deadIllAnimalCount zmienna typu integer do obliczania liczby matwych zwierząt z powodu chorób
     */
    private Integer deadIllAnimalCount;
    /**
     * cowDeathCount zmienna typu integer do oblicznia liczby matwych krów ze starości
     */
    private Integer cowDeathCount;
    /**
     * cowIllDeathCount zmienna typu integer do obliczania liczby matwych krów z powodu chorób
     */
    private Integer cowIllDeathCount;
    /**
     * chickenDeathCount zmienna typu integer do oblicznia liczby matwych kurczaków ze starości
     */
    private Integer chickenDeathCount;
    /**
     * chickenIllDeathCount zmienna typu integer do obliczania liczby matwych kurczaków z powodu chorób
     */
    private Integer chickenIllDeathCount;
    /**
     * sheepDeathCount zmienna typu integer do oblicznia liczby matwych owiec ze starości
     */
    private Integer sheepDeathCount;
    /**
     * sheepIllDeathCount zmienna typu integer do obliczania liczby matwych owiec z powodu chorób
     */
    private Integer sheepIllDeathCount;
    /**
     * bullDeathCount zmienna typu integer do oblicznia liczby matwych byków ze starości
     */
    private Integer bullDeathCount;
    /**
     * bullIllDeathCount zmienna typu integer do obliczania liczby matwych byków z powodu chorób
     */
    private Integer bullIllDeathCount;
    /**
     * cockDeathCount zmienna typu integer do oblicznia liczby matwych kogutów ze starości
     */
    private Integer cockDeathCount;
    /**
     * cockIllDeathCount zmienna typu integer do obliczania liczby matwych kogutów z powodu chorób
     */
    private Integer cockIllDeathCount;
    /**
     * ramDeathCount zmienna typu integer do oblicznia liczby matwych baranów ze starości
     */
    private Integer ramDeathCount;
    /**
     * ramIllDeathCount zmienna typu integer do obliczania liczby matwych baranów z powodu chorób
     */
    private Integer ramIllDeathCount;
    /**
     * RentalPayment zmienna typu double do przechowywania danych o kosztu dzierżawy gruntu pod gospodarstwo
     */
    private Double RentalPayment;

    /**
     * Metod do inicjalizacji gospodarstwa, zakupu wprowadzonych numerów każdego rodzaju zwierząt
     * i obliczenia wymaganej liczby samców do poprawnego działania symulacji i dodania ich do listy zwięrząt
     * @param cowCount liczba krów do zakupu
     * @param chickenCount liczba kurczaków do zakupu
     * @param sheepCount liczba owiec do zakupu
     */
    public void initializeFarm(Integer cowCount, Integer chickenCount, Integer sheepCount) {
        animals = new ArrayList<>();
        deadAnimalCount = 0;
        deadIllAnimalCount = 0;
        cowDeathCount = 0;
        chickenDeathCount = 0;
        sheepDeathCount = 0;
        bullDeathCount = 0;
        cockDeathCount = 0;
        ramDeathCount = 0;
        cowIllDeathCount = 0;
        chickenIllDeathCount = 0;
        sheepIllDeathCount = 0;
        bullIllDeathCount = 0;
        cockIllDeathCount = 0;
        ramIllDeathCount = 0;
        RentalPayment = 99000.0;
        Random random = new Random();

        //Pętle, które przypisują każdemu zwierzęciu każdego typu losowy wiek z dozwolonego limitu
        for (Integer i = 0; i < cowCount; i++) {
            Integer age = random.nextInt(18) + 1;
            animals.add(new Cow(age));
        }

        for (Integer i = 0; i < chickenCount; i++) {
            Integer age = random.nextInt(6) + 1;
            animals.add(new Chicken(age));
        }

        for (Integer i = 0; i < sheepCount; i++) {
            Integer age = random.nextInt(15) + 1;
            animals.add(new Sheep(age));
        }
        Integer bullCount = 0;
        Integer cockCount = 0;
        Integer ramCount = 0;
        if(cowCount != 0){
            bullCount = (int) (cowCount * 0.2) + 1;
        }

        if(chickenCount != 0){
            cockCount = (int) (cowCount * 0.33) + 1;
        }
        if(sheepCount != 0){
            ramCount = (int) (cowCount * 0.15) + 1;
        }

        for (int i = 0; i < bullCount; i++) {
            Integer age = random.nextInt(20) + 1;
            animals.add(new Bull(age));
        }
        for (int i = 0; i < cockCount; i++) {
            Integer age = random.nextInt(9) + 1;
            animals.add(new Cock(age));
        }
        for (int i = 0; i < ramCount; i++) {
            Integer age = random.nextInt(16) + 1;
            animals.add(new Ram(age));
        }
        yearlyProfits = new ArrayList<>();
        totalProfit = 0.0;
    }

    /**
     * Metoda odpowiadająca za hodowlę zwierząt każdego typu, pobiera dane
     * hodowlane z abstrakcyjnej klasy 'Animal' dla każdego typu zwierzęcia.
     * Ponadto metoda natychmiast odfiltrowuje zwierzęta według płci na samce
     * i samice i sprawdza, czy rozmnażanie jest możliwe według wieku każdego
     * zwierzęcia, wszystkie parametry do tego są również pobierane z klasy abstrakcyjnej 'Animal'.
     * @param random parametr do dziaania hodowli
     */
    public void reproduceAnimals(Random random) {
        List<Animal> newAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.reproduce(random)) {
                Integer age = 0;
                if (animal instanceof Cow) {
                    Cow cow = (Cow) animal;
                    if (cow.isMale(random)) {
                        newAnimals.add(new Bull(0));
                    } else {
                        newAnimals.add(new Cow(age));
                    }
                } else if (animal instanceof Chicken) {
                    Chicken chicken = (Chicken) animal;
                    if (chicken.isMale(random)) {
                        newAnimals.add(new Cock(0));
                    } else {
                        newAnimals.add(new Chicken(age));
                    }
                } else if (animal instanceof Sheep) {
                    Sheep sheep = (Sheep) animal;
                    if (sheep.isMale(random)) {
                        newAnimals.add(new Ram(0));
                    } else {
                        newAnimals.add(new Sheep(age));

                    }
                }
            }
        }

        animals.addAll(newAnimals);
    }

    /**
     * Metoda, która oblicza zysk każdego zwierzęcia każdego typu zgodnie z danymi z klasy
     * abstrakcyjnej i dodaje do zmiennej całkowity zysk oraz odejmuje roczny koszt dzierżawy ziemi.
     * @return zwraca zysk gospodarstwa.
     */
    public Double calculateProfit() {
        Double profit = 0.0;

        for (Animal animal : animals) {
            profit += animal.calculateProfit();
        }

        yearlyProfits.add(profit - RentalPayment);
        totalProfit += (profit - RentalPayment);

        return profit - RentalPayment;
    }

    /**
     * Metoda, która zwraca wynikowy zysk
     * @return zwraca wynikowy zusk
     */
    public Double getTotalProfit() {
        return totalProfit;
    }

    /**
     * Metoda, która zwraca listę rocznych zysków
     * @return zwraca listę rocznych zysków
     */
    public List<Double> getYearlyProfits() {
        return yearlyProfits;
    }

    /**
     * Metoda, która sprawdza, czy są martwe zwierzęta według ich wieku,
     * porównując aktualny wiek z maksymalnym możliwym wiekiem dla każdego rodzaju zwierząt.
     * Jeśli wiek osiągnął maksimum, zwierzę jest uważane za martwe, jest usuwane z listy zwierząt,
     * a wszystkie liczniki zmieniają się w zależności od rodzaju zwierzęcia.
     * @param random parametr do wybora róznych zwierząt
     */
    public void removeAnimals(Random random) {
        List<Animal> deadAnimals = new ArrayList<>();
        Integer deadCowCount = 0;
        Integer deadChickenCount = 0;
        Integer deadSheepCount = 0;
        Integer deadBullCount = 0;
        Integer deadCockCount = 0;
        Integer deadRamCount = 0;

        for (Animal animal : animals) {
            if (animal.getAge() >= animal.getMaxAge()) {
                deadAnimals.add(animal);
                if (animal instanceof Cow) {
                    deadCowCount++;
                }
                if (animal instanceof Chicken) {
                    deadChickenCount++;
                }
                if (animal instanceof Sheep) {
                    deadSheepCount++;
                }
                if (animal instanceof Bull) {
                    deadBullCount++;
                }
                if (animal instanceof Cock) {
                    deadCockCount++;
                }
                if (animal instanceof Ram) {
                    deadRamCount++;
                }
            }
        }
        deadAnimalCount += deadAnimals.size();
        cowDeathCount += deadCowCount;
        chickenDeathCount += deadChickenCount;
        sheepDeathCount += deadSheepCount;
        bullDeathCount += deadBullCount;
        cockDeathCount += deadCockCount;
        ramDeathCount += deadRamCount;
        animals.removeAll(deadAnimals);
    }

    /**
     * Metod, do dostania liczby martwych krów ze starości
     * @return zwraca liczbe martwych krów ze starości
     */
    public Integer getDeadCowCount() {
        return cowDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych kurczków ze starości
     * @return zwraca liczbe martwych kurczaków ze starości
     */
    public Integer getDeadChickenCount() {
        return chickenDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych owiec ze starości
     * @return zwraca liczbe martwych owiec ze starości
     */
    public Integer getDeadSheepCount() {
        return sheepDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych byków ze starości
     * @return zwraca liczbe martwych byków ze starości
     */
    public Integer getDeadBullCount() {
        return bullDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych kogutów ze starości
     * @return zwraca liczbe martwych kogutów ze starości
     */
    public Integer getDeadCockCount() {
        return cockDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych baranów ze starości
     * @return zwraca liczbe martwych baranów ze starości
     */
    public Integer getDeadRamCount() {
        return ramDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych zwięrząt ze starości
     * @return zwraca liczbe martwych zwięrząt ze starości
     */
    public Integer getDeadAnimalCount() {
        return deadAnimalCount;
    }

    /**
     * Metoda, która symuluje śmierć spowodowaną chorobą poprzez
     * losowe usuwanie zwierząt każdego gatunku z listy zwierząt.
     * Podczas usuwania zwierząt określonego typu zmienia wszystkie liczniki.
     * @param random parametr do losowego usuwania zwierząt.
     */
    public void removeIllAnimals(Random random) {
        List<Animal> deadIllAnimals = new ArrayList<>();
        Integer deadIllCowCount = 0;
        Integer deadIllChickenCount = 0;
        Integer deadIllSheepCount = 0;
        Integer deadIllBullCount = 0;
        Integer deadIllCockCount = 0;
        Integer deadIllRamCount = 0;

        for (Animal animal : animals) {
            Integer illness = random.nextInt(10) + 1;
            if (illness >= 9) {
                deadIllAnimals.add(animal);
                if (animal instanceof Cow) {
                    deadIllCowCount++;
                }
                if (animal instanceof Chicken) {
                    deadIllChickenCount++;
                }
                if (animal instanceof Sheep) {
                    deadIllSheepCount++;
                }
                if (animal instanceof Bull) {
                    deadIllBullCount++;
                }
                if (animal instanceof Cock) {
                    deadIllCockCount++;
                }
                if (animal instanceof Ram) {
                    deadIllRamCount++;
                }
            }
        }
        deadIllAnimalCount += deadIllAnimals.size();
        cowIllDeathCount += deadIllCowCount;
        chickenIllDeathCount += deadIllChickenCount;
        sheepIllDeathCount += deadIllSheepCount;
        bullIllDeathCount += deadIllBullCount;
        cockIllDeathCount += deadIllCockCount;
        ramIllDeathCount += deadIllRamCount;
        animals.removeAll(deadIllAnimals);
    }

    /**
     * Metod, do dostania liczby martwych krów z powodu chorób
     * @return zwraca liczbe martwych krów z powodu chorób
     */
    public Integer getDeadIllCowCount() {
        return cowIllDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych kurczaków z powodu chorób
     * @return zwraca liczbe martwych kurczaków z powodu chorób
     */
    public Integer getDeadIllChickenCount() {
        return chickenIllDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych owiec z powodu chorób
     * @return zwraca liczbe martwych owiec z powodu chorób
     */
    public Integer getDeadIllSheepCount() {
        return sheepIllDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych byków z powodu chorób
     * @return zwraca liczbe martwych byków z powodu chorób
     */
    public Integer getDeadIllBullCount() {
        return bullIllDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych kogutów z powodu chorób
     * @return zwraca liczbe martwych kogutów z powodu chorób
     */
    public Integer getDeadIllCockCount() {
        return cockIllDeathCount;
    }

    /**
     * Metod, do dostania liczby martwych baranów z powodu chorób
     * @return zwraca liczbe martwych baranów z powodu chorób
     */
    public Integer getDeadIllRamCount() {
        return ramIllDeathCount;
    }
    /**
     * Metod, do dostania liczby martwych zwięrząt z powodu chorób
     * @return zwraca liczbe martwych zwięrząt z powodu chorób
     */
    public Integer getDeadIllAnimalCount() {
        return deadIllAnimalCount;
    }

    /**
     * Metoda na podwyższenie wieku wszystkich zwierząt
     */
    public void incrementAnimalAges() {
        for (Animal animal : animals) {
            animal.incrementAge();
        }
    }

    /**
     * Metoda wytwarzania produktów pochodzenia zwierzęcego
     * przy użyciu danych z klasy abstrakcyjnej 'Animal'
     */
    public void produceAnimalProducts() {
        for (Animal animal : animals) {
            if (animal instanceof Cow) {
                ((Cow) animal).produceMilk();
            } else if (animal instanceof Chicken) {
                ((Chicken) animal).layEgg();
            } else if (animal instanceof Sheep) {
                ((Sheep) animal).produceWool();
            }
        }
    }

    /**
     * Metoda uzyskania aktualnej liczby krów
     * @return zwraca aktualną liczbe krów
     */
    public Integer getCowCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Cow) {
                count++;
            }
        }
        return count;
    }

    /**
     * Metoda uzyskania aktualnej liczby kurczaków
     * @return zwraca aktualną liczbe kurczaków
     */
    public Integer getChickenCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Chicken) {
                count++;
            }
        }
        return count;
    }

    /**
     * Metoda uzyskania aktualnej liczby owiec
     * @return zwraca aktualną liczbe owiec
     */
    public Integer getSheepCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Sheep) {
                count++;
            }
        }
        return count;
    }

    /**
     * Metoda uzyskania aktualnej liczby byków
     * @return zwraca aktualną liczbe byków
     */
    public Integer getBullCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Bull) {
                count++;
            }
        }
        return count;
    }

    /**
     * Metoda uzyskania aktualnej liczby kogutów
     * @return zwraca aktualną liczbe kogutów
     */
    public Integer getCockCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Cock) {
                count++;
            }
        }
        return count;
    }

    /**
     * Metoda uzyskania aktualnej liczby baranów
     * @return zwraca aktualną liczbe baranów
     */
    public Integer getRamCount() {
        Integer count = 0;
        for (Animal animal : animals) {
            if (animal instanceof Ram) {
                count++;
            }
        }
        return count;
    }

}
