package br.com.zup.projeto_final.customException;

public class RecursoInexistente extends RuntimeException{
    public RecursoInexistente(String message) {
        super(message);
    }
}
