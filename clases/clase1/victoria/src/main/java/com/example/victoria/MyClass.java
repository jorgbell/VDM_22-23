package com.example.victoria;

public class MyClass {
    public static void main (String[] args){
        //RESUMEN DE ESTO: Cree una interfaz, que tiene un metodo. Lo implementa MyClass2 y MyClass3 extiende su funcionalidad

        //instrucciones
        System.out.println("bona tarda");
        //crear una clase
        MyClass2 class2 = new MyClass2();
        System.out.println(class2.s);
        MyClass3 class3 = new MyClass3();
        class3.SantEsteve();
    }
}