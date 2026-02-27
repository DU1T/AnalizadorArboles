package app.arbol;

import java.util.ArrayList;
import java.util.List;

public class Recorridos
{
    public static List<String> preorden(NodoGeneral<String> raiz) {
        List<String> resultado = new ArrayList<>();
        preordenRec(raiz, resultado);
        return resultado;
    }
    private static void preordenRec(NodoGeneral<String> nodo, List<String> resultado) {
        if (nodo == null) return;

        resultado.add(nodo.getDato());

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            preordenRec(hijo, resultado);
        }
    }

    public static List<String> postorden(NodoGeneral<String> raiz) {
        List<String> resultado = new ArrayList<>();
        postordenRec(raiz, resultado);
        return resultado;
    }

    private static void postordenRec(NodoGeneral<String> nodo, List<String> resultado) {
        if (nodo == null) return;

        for (NodoGeneral<String> hijo : nodo.getHijos()) {
            postordenRec(hijo, resultado);
        }

        resultado.add(nodo.getDato());
    }
    public List<String> inorden(NodoGeneral<String> nodo) {
        List<String> lista = new ArrayList<>();
        inordenRec(nodo, lista);
        return lista;
    }

    private void inordenRec(NodoGeneral<String> nodo,
                            List<String > lista) {

        if (nodo == null) return;

        if (!nodo.getHijos().isEmpty()) {
            inordenRec(nodo.getHijos().get(0), lista);
        }

        lista.add(nodo.getDato());

        for (int i = 1; i < nodo.getHijos().size(); i++) {
            inordenRec(nodo.getHijos().get(i), lista);
        }
    }
}
