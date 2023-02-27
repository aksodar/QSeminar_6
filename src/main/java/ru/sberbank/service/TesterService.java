package ru.sberbank.service;

import ru.sberbank.data.Tester;

import java.util.ArrayList;

public class TesterService extends AbstractStorage<Tester> implements DataService<Tester>{

    public TesterService(){
        super();
    }

    @Override
    public Tester create(int id, String firstName, String secondName) throws IllegalStateException {
        if((firstName == null || firstName.isEmpty()) || (secondName == null || secondName.isEmpty())) {
            throw new IllegalStateException("Входные данные не валидны");
        }
        Tester tester = new Tester(id, firstName, secondName);
        list.add(tester);
        return tester;
    }

    @Override
    public Tester get(String firstName, String secondName) throws IllegalStateException {
        for (Tester t: list)  {
            if(firstName.equalsIgnoreCase(t.getFirstName()) && secondName.equalsIgnoreCase(t.getSecondName())) {
                return t;
            }
        }
        throw new IllegalStateException("Тестировщики не найдены");
    }

    @Override
    public ArrayList<Tester> getListOfFree() {
        ArrayList<Tester> freeTester = new ArrayList<>();
        for (Tester n: list) {
            if(n.isFree()) {
                freeTester.add(n);
            }
        }
        return freeTester;
    }
}
