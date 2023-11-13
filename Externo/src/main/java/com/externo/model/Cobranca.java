    package com.externo.model;
    
    import com.externo.DTO.CobrancaDTO;
    
    import java.util.ArrayList;
    import java.util.List;
    
    public class Cobranca {
    
        private int id;
        private StatusCobranca status;
        private String horaSolicitacao;
        private String horaFinalizacao;
        private float valor;
        private int ciclista;
    
        public static List<Cobranca> cobrancas = new ArrayList<>();
    
        public Cobranca(CobrancaDTO dadosCadastroCobranca) {
            this.id = cobrancas.size() + 1;
            this.status = dadosCadastroCobranca.status();
            this.horaSolicitacao = dadosCadastroCobranca.horaSolicitacao();
            this.horaFinalizacao = dadosCadastroCobranca.horaFinalizacao();
            this.valor = dadosCadastroCobranca.valor();
            this.ciclista = dadosCadastroCobranca.ciclista();
        }
    
        public void atualizaCobranca(CobrancaDTO dadosCadastroCobranca) {
            this.status = dadosCadastroCobranca.status();
            this.horaSolicitacao = dadosCadastroCobranca.horaSolicitacao();
            this.horaFinalizacao = dadosCadastroCobranca.horaFinalizacao();
            this.valor = dadosCadastroCobranca.valor();
            this.ciclista = dadosCadastroCobranca.ciclista();
        }
    
        public enum StatusCobranca {
            PENDENTE,
            PAGA,
            FALHA,
            CANCELADA,
            OCUPADA
        }

        public StatusCobranca getStatus() {
            return this.status;
        }

        public void setStatus(StatusCobranca status) {
            this.status = status;
        }

        public String getHoraSolicitacao() {
            return this.horaSolicitacao;
        }

        public void setHoraSolicitacao(String horaSolicitacao) {
            this.horaSolicitacao = horaSolicitacao;
        }

        public String getHoraFinalizacao() {
            return this.horaFinalizacao;
        }

        public void setHoraFinalizacao(String horaFinalizacao) {
            this.horaFinalizacao = horaFinalizacao;
        }

        public float getValor() {
            return this.valor;
        }

        public void setValor(float valor) {
            this.valor = valor;
        }

        public int getCiclista() {
            return this.ciclista;
        }

        public void setCiclista(int ciclista) {
            this.ciclista = ciclista;
        }
    
        public int getId() {
            return this.id;
        }
    }
