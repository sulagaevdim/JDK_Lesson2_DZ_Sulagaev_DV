package server.repository;

public interface Repositorable<String> {
    void saveInLog(String text);
    String readLog();
}
