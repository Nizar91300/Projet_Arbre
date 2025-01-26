package common;

import common.notification.NotifEvenement;

import java.util.HashSet;

public class ServiceEspaceVert {

    private static ServiceEspaceVert instance;
    HashSet<ServiceVertObserver> observers;

    public static ServiceEspaceVert get() {
        if (instance == null) {instance = new ServiceEspaceVert();}
        return instance;
    }

    private ServiceEspaceVert() {
        observers = new HashSet<>();
        addObserver(AssociationVert.get());
    }

    public void addObserver(ServiceVertObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(ServiceVertObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(NotifEvenement notification) {
        for (ServiceVertObserver observer : observers) {
            observer.notify(notification);
        }
    }

    public void clearObservers(){
        observers.clear();
    }

}
