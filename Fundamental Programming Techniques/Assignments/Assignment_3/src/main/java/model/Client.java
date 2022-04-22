package model;


/**
 * Clasa care implementeaza Model Classes pentru tabelul Client.
 */
public class Client {
    private int idClient;
    private String name;
    private String address;
    private int age;

    public Client(int idClient, String name, String address, int age) {
        this.idClient = idClient;
        this.name = name;
        this.address = address;
        this.age = age;
    }

    public Client() {

    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int id) {
        this.idClient = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }


    @Override
    public String toString() {
        return "Client [id=" + idClient + ", name=" + name + ", address=" + address + ", age=" + age
                + "]";
    }


}

