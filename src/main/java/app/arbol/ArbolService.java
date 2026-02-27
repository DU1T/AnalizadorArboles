package app.arbol;

import java.util.List;
import java.util.Map;

public class ArbolService {

    private ArbolModel modelo;

    public ArbolService()
    {
        modelo = new ArbolModel();
    }

    public void insertar(String valor, String padre)
    {
        modelo.Insertar(valor, padre);
    }

    public String obtenerRaiz()
    {
        return modelo.GetRaiz() != null
                ? modelo.GetRaiz().getDato()
                : null;
    }
    public int contarCaminos(int longitud)
    {
        return modelo.GetCaminosLongitud(longitud);
    }
    public NodoGeneral<String> obtenerNodoRaiz()
    {
        return modelo.GetRaiz();
    }

    public int alturaArbol()
    {
        return modelo.GetAltura();
    }

    public int alturaNodo(String valor)
    {
        return modelo.GetAlturaDeNodo(valor);
    }

    public int profundidadNodo(String valor)
    {
        return modelo.GEtProfundidadNodo(valor);
    }

    public List<String> ancestros(String valor)
    {
        return modelo.GetAncestros(valor);
    }

    public List<String> descendientes(String valor)
    {
        return modelo.GetDescendientes(valor);
    }

    public List<String> hojas()
    {
        return modelo.GetHojas();
    }

    public boolean esCamino(List<String> secuencia)
    {
        return modelo.esCamino(secuencia);
    }

    public String hermanoDerecho(String valor)
    {
        return modelo.HermanoDerecho(valor);
    }

    public List<String> preorden()
    {
        return Recorridos.preorden(modelo.GetRaiz());
    }

    public List<String> postorden()
    {
        return Recorridos.postorden(modelo.GetRaiz());
    }

    public List<String> inorden()
    {
        Recorridos r = new Recorridos();
        return r.inorden(modelo.GetRaiz());
    }

    public Map<Integer, List<String>> niveles()
    {
        return modelo.GetNiveles();
    }
    public boolean existeNodo(String valor)
    {
        return modelo.buscar(valor) != null;
    }

    public void reset()
    {
        modelo = new ArbolModel();
    }
    public List<String> ancestrosPropios(String valor) {
        return modelo.GetAncestrosPropios(valor);
    }

    public List<String> hijos(String valor) {
        return modelo.GetHijos(valor);
    }

    public List<String> hermanos(String valor) {
        return modelo.GetHermanos(valor);
    }

    public String hermanoIzquierdo(String valor) {
        return modelo.HermanoIzquierdo(valor);
    }

    public boolean estaALaDerechaDe(String referencia, String posible) {
        return modelo.EstaALaDerechaDe(referencia, posible);
    }
}