package common;

import java.util.HashSet;

public class ServiceEspaceVert implements Donateur{

    private static ServiceEspaceVert instance;
    HashSet<ServiceVertObserver> observers;

    public static ServiceEspaceVert get() {
        if (instance == null) {instance = new ServiceEspaceVert();}
        return instance;
    }

    private ServiceEspaceVert() {
        observers = new HashSet<>();
    }

    public void addObserver(ServiceVertObserver observer) {
        observers.add(observer);
    }
    public void removeObserver(ServiceVertObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Notification notification) {
        for (ServiceVertObserver observer : observers) {
            observer.notify(notification);
        }
    }

}
