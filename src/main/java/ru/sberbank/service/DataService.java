package ru.sberbank.service;

import ru.sberbank.data.TeamMember;

import java.util.ArrayList;

public interface DataService<T extends TeamMember> {
    T create(int id, String firstName, String secondName) throws IllegalStateException;
    T get(String firstName, String secondName) throws IllegalStateException;
    ArrayList<T> getListOfFree();
}
