package app.arbol;

import java.util.*;

public class ArbolModel
{
    //Atributos
    private NodoGeneral<String> raiz;
    private boolean esBalanceado;
    private boolean esPerfecto;
    private int maxHijos = 4;

    //Constructor
    public ArbolModel()
    {
        this.raiz = null;
    }
    //Metodos
    //Setters
    public void SetMaxHijos(int maxHijos)
    {
        this.maxHijos = maxHijos;
    }
        //Si padre es null, es la raiz.
    public void Insertar(String valor, String valorPadre)
    {

        if (raiz == null)
        {
            raiz = new NodoGeneral<String>(valor);
            return;
        }

        ResultadoBusqueda resultado = buscarExistenteYPadre(raiz, valor, valorPadre);

        NodoGeneral<String> existente = resultado.getExistente();
        NodoGeneral<String> padre = resultado.getPadre();

        if (existente != null) //No duplicados
        {
            return;
        }

        if (padre != null)
        {
            padre.agregarHijo(new NodoGeneral<>(valor));
        }
    }

    //Getters
    //Raiz
    public NodoGeneral<String> GetRaiz()
    {
        return raiz;
    }
    //Caminos
    public int GetCaminosLongitud(int longitud) {
        return contarCaminosRec(raiz, longitud);
    }
    //Altura
    public int GetAltura()
    {
        return calcularAltura(raiz);
    }
    public int GetAlturaDeNodo(String valor)
    {
        NodoGeneral<String> nodo = buscar(valor);
        return calcularAltura(nodo);
    }
    //Profundidad
    public int GEtProfundidadNodo(String valor)
    {
        return profundidadRec(raiz, valor, 0);
    }
    //Equilibrio
    public String GetTipoEquilibrio()
    {

        if (raiz == null) return "Vacio";

        esBalanceado = true;
        esPerfecto = true;

        analizar(raiz);

        if (esPerfecto) return "Perfectamente Equilibrado";
        else if (esBalanceado) return "Equilibrado";

        return "No equilibrado";

    }
    //Niveles
    public Map<Integer, List<String>> GetNiveles() {

        Map<Integer, List<String>> niveles = new LinkedHashMap<>();

        if (raiz == null) return niveles;

        Queue<NodoGeneral<String>> cola = new LinkedList<>();
        Queue<Integer> nivelesCola = new LinkedList<>();

        cola.add(raiz);
        nivelesCola.add(0);

        while (!cola.isEmpty()) {

            NodoGeneral<String> actual = cola.poll();
            int nivel = nivelesCola.poll();

            niveles.putIfAbsent(nivel, new ArrayList<>());
            niveles.get(nivel).add(actual.getDato());

            for (NodoGeneral<String> hijo : actual.getHijos()) {
                cola.add(hijo);
                nivelesCola.add(nivel + 1);
            }
        }

        return niveles;
    }
    //Busqueda publica
    public NodoGeneral<String> buscar(String valor) {
        return buscarRecursivo(raiz, valor);
    }
    //ANcestros propios
    public List<String> GetAncestrosPropios(String valor) {
        List<String> lista = GetAncestros(valor);
        return lista;
    }
    //Ancestros
    public List<String> GetAncestros(String valor) {
        List<String> lista = new ArrayList<>();
        obtenerAncestrosRec(raiz, valor, lista);
        return lista;
    }
    //Descendientes
    public List<String> GetDescendientes(String valor) {
        List<String> lista = new ArrayList<>();
        NodoGeneral<String> nodo = buscar(valor);

        if (nodo != null) {
            for (NodoGeneral<String> hijo : nodo.getHijos()) {
                obtenerDescendientesRec(hijo, lista);
            }
        }

        return lista;
    }
    //Hojas
    public List<String> GetHojas() {
        List<String> hojas = new ArrayList<>();
        obtenerHojasRec(raiz, hojas);
        return hojas;
    }



    //Helpers
    //Buscar
    private static class ResultadoBusqueda {

        private NodoGeneral<String> existente;
        private NodoGeneral<String> padre;

        public ResultadoBusqueda(NodoGeneral<String> existente,
                                 NodoGeneral<String> padre) {
            this.existente = existente;
            this.padre = padre;
        }

        public NodoGeneral<String> getExistente() {
            return existente;
        }

        public NodoGeneral<String> getPadre() {
            return padre;
        }
    }
    private ResultadoBusqueda buscarExistenteYPadre(
            NodoGeneral<String> actual,
            String valor,
            String valorPadre) {

        if (actual == null) {
            return new ResultadoBusqueda(null, null);
        }

        NodoGeneral<String> existente = null;
        NodoGeneral<String> padre = null;

        if (actual.getDato().equals(valor)) {
            existente = actual;
        }

        if (actual.getDato().equals(valorPadre)) {
            padre = actual;
        }

        for (NodoGeneral<String> hijo : actual.getHijos()) {

            ResultadoBusqueda resultado = buscarExistenteYPadre(hijo, valor, valorPadre);

            if (resultado.getExistente() != null)
            {
                existente = resultado.getExistente();
            }

            if (resultado.getPadre() != null)
            {
                padre = resultado.getPadre();
            }
            if (existente != null && padre != null)
            {
                break;
            }
        }

        return new ResultadoBusqueda(existente, padre);
    }
    //Altura
    private int calcularAltura(NodoGeneral<String> nodo)
    {
        if (nodo == null) return 0;
        if (nodo.esHoja()) return 1;
        int maxH = 0;
        for (NodoGeneral<String> hijo : nodo.getHijos())
        {
            maxH = Math.max(maxH, calcularAltura(hijo));
        }
        return 1 + maxH;
    }
    //Equilibrio
    private int analizar(NodoGeneral<String> nodo) {

        if (nodo == null) return 0;

        if (nodo.esHoja()) return 1;

        int minH = Integer.MAX_VALUE;
        int maxH = Integer.MIN_VALUE;

        for (NodoGeneral<String> hijo : nodo.getHijos())
        {

            int h = analizar(hijo);

            minH = Math.min(minH, h);
            maxH = Math.max(maxH, h);
        }

        // Verificar equilibrio
        if (maxH - minH > 1)
        {
            esBalanceado = false;
        }

        // Verificar perfeccion
        if (maxH != minH)
        {
            esPerfecto = false;
        }

        return maxH + 1;
    }
    //Busqueda recursiva
    private NodoGeneral<String> buscarRecursivo(
            NodoGeneral<String> actual,
            String valor) {

        if (actual == null) return null;

        if (actual.getDato().equals(valor)) {
            return actual;
        }

        for (NodoGeneral<String> hijo : actual.getHijos()) {
            NodoGeneral<String> encontrado = buscarRecursivo(hijo, valor);
            if (encontrado != null) return encontrado;
        }

        return null;
    }
    //Caminos
    private int contarCaminosRec(NodoGeneral<String> nodo, int longitud) {
        if (nodo == null) return 0;

        if (longitud == 0) return 1;

        int total = 0;
        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            total += contarCaminosRec(hijo, longitud - 1);
        }

        return total;
    }
    public boolean esCamino(List<String> secuencia) {
        if (raiz == null || secuencia.isEmpty()) return false;

        if (!raiz.getDato().equals(secuencia.get(0))) return false;

        NodoGeneral<String> actual = raiz;

        for (int i = 1; i < secuencia.size(); i++) {

            boolean encontrado = false;

            for (NodoGeneral<String> hijo : actual.getHijos()) {
                if (hijo.getDato().equals(secuencia.get(i))) {
                    actual = hijo;
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) return false;
        }

        return true;
    }
    //Ancestros
    private boolean obtenerAncestrosRec(NodoGeneral<String> actual,
                                        String valor,
                                        List<String> lista)
    {

        if (actual == null) return false;

        if (actual.getDato().equals(valor)) {
            return true;
        }

        for (NodoGeneral<String> hijo : actual.getHijos()) {
            if (obtenerAncestrosRec(hijo, valor, lista)) {
                lista.add(actual.getDato());
                return true;
            }
        }

        return false;
    }
    //DEscedientes
    private void obtenerDescendientesRec(NodoGeneral<String> nodo,
                                         List<String> lista) {

        if (nodo == null) return;

        lista.add(nodo.getDato());

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            obtenerDescendientesRec(hijo, lista);
        }
    }
    //Hojas
    private void obtenerHojasRec(NodoGeneral<String> nodo,
                                 List<String> hojas) {

        if (nodo == null) return;

        if (nodo.esHoja()) {
            hojas.add(nodo.getDato());
            return;
        }

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            obtenerHojasRec(hijo, hojas);
        }
    }
    //Profundidad
    private int profundidadRec(NodoGeneral<String> actual,
                               String valor,
                               int nivel) {

        if (actual == null) return -1;

        if (actual.getDato().equals(valor)) return nivel;

        for (NodoGeneral<String> hijo : actual.getHijos()) {
            int resultado = profundidadRec(hijo, valor, nivel + 1);
            if (resultado != -1) return resultado;
        }

        return -1;
    }
    //Hermanos
    public String HermanoDerecho(String valor) {

        if (raiz == null) return null;

        Queue<NodoGeneral<String>> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {

            NodoGeneral<String> actual = cola.poll();
            List<NodoGeneral<String>> hijos = actual.getHijos();

            for (int i = 0; i < hijos.size(); i++) {
                if (hijos.get(i).getDato().equals(valor)) {
                    if (i + 1 < hijos.size()) {
                        return hijos.get(i + 1).getDato();
                    }
                }
            }

            cola.addAll(hijos);
        }

        return null;
    }
    public boolean EstaALaDerechaDe(String referencia, String posible)
    {
        String hermano = HermanoDerecho(referencia);
        return hermano != null && hermano.equals(posible);
    }

}
