import java.util.*

fun main() {
    println("Hola Mundo")

    // INMUTABLES (No se RE ASIGNA "=")
    val inmutable: String = "Ana";
    // inmutable = "Vicente" // Error!
    // MUTABLES
    var mutable: String = "Belen"
    mutable = "Ana" // Ok
    // VAL > VAR



    // Duck Typing
    val ejemploVariable = " Ana Campoverde "
    ejemploVariable.trim()
    val edadEjemplo: Int = 12
    // ejemploVariable = edadEjemplo // Error!
    // Variables Primitivas
    val nombreProfesor: String = "Ana Campoverde"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad:Boolean = true
    // Clases en Java
    val fechaNacimiento: Date = Date()


    // When (Switch)
    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") ->{
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else ->{
            println("No sabemos")
        }
    }
    val esSoltero = (estadoCivilWhen == "S")
    val coqueteo = if (esSoltero) "Si" else "No" // if else chiquito


    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00,20.00)
    // Named parameters
    // calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo=10.00, tasa=14.00)
    // Uso de clases
    val sumaUno = Suma(1,1) // new Suma(1,1) en KOTLIN no hay "new"
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1,null)
    val sumaCuatro = Suma(null,null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()
    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)

    // Arreglos
    // Estaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico);
    // Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1,2,3,4,5,6,7,8,9,10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    println(arregloDinamico)

    // FOR EACH = > Unit
    // Iterar un arreglo
    val respuestaForEach: Unit = arregloDinamico
        .forEach{ valorActual: Int ->
            println("Valor actual: ${valorActual}");
        }
    // "it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor Actual (it): ${it}")}


}// Termina funcion Main

// void -> Unit
fun imprimirNombre(nombre:String): Unit{
    println("Nombre: ${nombre}") // Template Strings
}
fun calcularSueldo(
    sueldo:Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial:Double? = null // Opcional (nullable)
    // Variable? -> "?" Es Nullable (osea que puede en algun momento ser nulo)
):Double {
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        return sueldo * (100/tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno:Int
    private val numeroDos: Int
    constructor(
        uno:Int,
        dos:Int
    ){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros( // Constructor Primario
    // Caso 1) Parametro normal
    // uno:Int , (parametro (sin modificador acceso))

    // Caso 2) Parametro y propiedad (atributo) (protected)
    // private var uno: Int (propiedad "instancia.uno")
    protected val numeroUno: Int, // instancia.numeroUno
    protected val numeroDos: Int, // instancia.numeroDos
){
    init { // bloque constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}



class Suma( // Constructor primario
    unoParametro: Int, // Parametro
    dosParametro: Int, // Parametros
): Numeros( // Clase papa, Numeros (extendiendo)
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito:String = "Explicito" // Publicas
    val soyPublicoImplicito:String = "Implicito" // Publicas (propiedades, metodos)
    init{ // Bloque Codigo Constructor primario
        this.numeroUno
        this.numeroDos
        numeroUno // this. OPCIONAL (propiedades, metodos)
        numeroDos // this. OPCIONAL (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito // this. OPCIONAL (propiedades, metodos)
    }



    constructor( // Constructor secundario
        uno:Int?,
        dos:Int
    ):this(
        if(uno== null) 0 else uno,
        dos
    )
    constructor( // Constructor tercero
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if(dos== null) 0 else dos,
    )

    constructor( // Constructor cuarto
        uno:Int?,
        dos:Int?
    ):this(
        if(uno== null) 0 else uno,
        if(dos== null) 0 else dos,
    )
    // public fun sumar():Int{
    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)
        // ("Suma." o "NombreClase." es OPCIONAL)
        agregarHistorial(total)
        return total
    }
    companion object { // Comparte entre todas las instancias,
        // similar al Static
        // funciones y variables
        val pi = 3.14 // Suma.pi
        fun elevarAlCuadrado(num:Int):Int{ // Suma.elevarAlCuadrado
            return num * num
        }
        val historialSumas = arrayListOf<Int>() // Suma.historialSumas
        fun agregarHistorial(valorTotalSuma:Int){ // Suma.agregarHistorial
            historialSumas.add(valorTotalSuma)
        }
    }


}
