// Guarda os textos do console (mensagem de boas-vindas e o menu de opções),
// separado do Main pra não misturar texto fixo com lógica do programa.
public class Menu {
    private String menuEntrada = """
            !!! Seja bem vindo à Central de Atendimento e Serviços !!!
            !!! Preste atenção nas opções a seguir !!!
            #########################################################
            """;
    private String menuPrincipal = """
            ========================================
             CENTRAL DE ATENDIMENTO
            ========================================
            1 - Cadastrar nova solicitação
            2 - Consultar próxima solicitação
            3 - Atender próxima solicitação
            4 - Exibir fila de solicitações
            5 - Exibir quantidade de solicitações
            6 - Consultar última operação realizada
            7 - Exibir histórico de operações
            8 - Desfazer última operação
            9 - Gerar solicitações de teste automaticamente
            0 - Encerrar
            """;

    public String getMenuEntrada() {
        return menuEntrada;
    }

    public String getMenuPrincipal() {
        return menuPrincipal;
    }
}
