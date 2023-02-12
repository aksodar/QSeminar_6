package ru.sberbank.service;

import ru.sberbank.data.Developer;

import java.util.ArrayList;

public class DeveloperService extends AbstractStorage<Developer> implements DataService<Developer> {

    public DeveloperService() {
        super();
    }

    @Override
    public Developer create(int id, String firstName, String secondName) throws IllegalStateException {
        if ((firstName == null || firstName.isEmpty()) || (secondName == null || secondName.isEmpty())) {
            throw new IllegalStateException("Входные данные не валидны");
        }
        Developer developer = new Developer(id, firstName, secondName);
        list.add(developer);
        return developer;
    }

    @Override
    public Developer get(String firstName, String secondName) throws IllegalStateException {
        for (Developer t : list) {
            if (firstName.equalsIgnoreCase(t.getFirstName()) && secondName.equalsIgnoreCase(t.getSecondName())) {
                return t;
            }
        }
        throw new IllegalStateException("Разработчик не найден");
    }

    @Override
    public ArrayList<Developer> getListOfFree() {
        ArrayList<Developer> freeTester = new ArrayList<>();
        for (Developer n : list) {
            if (n.isFree()) {
                freeTester.add(n);
            }
        }
        return freeTester;
    }
}