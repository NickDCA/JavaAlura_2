package br.com.alura.screenmatch.principal;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.alura.screenmatch.excecao.ErroDeConversaoDeAnoException;
import br.com.alura.screenmatch.modelos.Titulo;
import br.com.alura.screenmatch.modelos.TituloOmdb;

public class PrincipalComBusca {
        public static void main(String[] args) throws IOException, InterruptedException {
                Scanner leitura = new Scanner(System.in);
                System.out.println("Digite um filme para busca: ");
                var busca = leitura.nextLine();

                String endereco = "http://www.omdbapi.com/?t=" + busca.replace(" ", "+") + "&apikey=c2ab0658";

                try {
                        HttpClient client = HttpClient.newHttpClient();
                        HttpRequest request = HttpRequest.newBuilder()
                                        .uri(URI.create(endereco))
                                        .build();
                        HttpResponse<String> response = client
                                        .send(request, HttpResponse.BodyHandlers.ofString());

                        String json = response.body();
                        System.out.println(json);

                        Gson gson = new GsonBuilder()
                                        .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                                        .create();

                        TituloOmdb meuTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                        System.out.println(meuTituloOmdb);

                        Titulo meuTitulo = new Titulo(meuTituloOmdb);
                        System.out.println("Titulo já convertido");
                        System.out.println(meuTitulo);
                } catch (NumberFormatException e) {
                        System.out.println("Aconteceu um erro: ");
                        System.out.println(e.getMessage());
                } catch (IllegalArgumentException e) {
                        System.out.println("Algum erro de argumento na busca, verigique o endereço");
                        System.out.println(e.getMessage());
                } catch (ErroDeConversaoDeAnoException e) {
                        System.out.println(e.getMessage());
                }

                System.out.println("Programa finalizou corretamente");

                leitura.close();
        }
}
