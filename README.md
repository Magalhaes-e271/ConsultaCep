# ConsultaCep 📍

Aplicação Android desenvolvida em **Kotlin** que permite consultar informações de endereço através de um CEP utilizando a API **ViaCEP**.

## 📋 Sobre o Projeto

ConsultaCep é um aplicativo Android moderno que integra-se com a API ViaCEP para fornecer informações completas de endereço (logradouro, bairro, cidade, estado e DDD) a partir de um código de endereçamento postal (CEP) válido.

### Funcionalidades Principais

- ✅ **Busca por CEP**: Consultar endereço completo inserindo apenas o CEP
- ✅ **Formatação Automática**: CEP é formatado automaticamente (XX.XXX-XXX)
- ✅ **Validação em Tempo Real**: Validação do CEP enquanto o usuário digita
- ✅ **Preenchimento Automático**: Os campos de endereço são preenchidos automaticamente
- ✅ **Interface Responsiva**: Design moderno com Material Design
- ✅ **Requisições Assíncronas**: Usa Coroutines para operações não bloqueantes

## 🛠️ Tecnologias Utilizadas

| Tecnologia | Versão | Descrição |
|-----------|--------|-----------|
| **Kotlin** | 1.9+ | Linguagem de programação |
| **Android SDK** | 36 | SDK do Android |
| **Retrofit** | 2.11.0 | Cliente HTTP para consumir APIs REST |
| **Gson** | 2.11.0 | Biblioteca para serialização/desserialização JSON |
| **AndroidX** | Latest | Bibliotecas modernas do Android |
| **Material Design** | Latest | Componentes de UI do Material Design |
| **Coroutines** | Latest | Programação assíncrona em Kotlin |

## 📁 Estrutura do Projeto

```
ConsultaCep/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/consultacep/
│   │   │   │   ├── MainActivity.kt              # Atividade principal da app
│   │   │   │   ├── api/
│   │   │   │   │   ├── ViaCepClient.kt         # Cliente Retrofit singleton
│   │   │   │   │   └── ViaCepService.kt        # Interface do serviço ViaCEP
│   │   │   │   └── model/
│   │   │   │       └── ResponseEndereco.kt     # Modelo de dados do endereço
│   │   │   └── res/                            # Recursos (layouts, strings, etc)
│   │   ├── test/                               # Testes unitários
│   │   └── androidTest/                        # Testes instrumentados
│   ├── build.gradle.kts                        # Configuração Gradle do módulo
│   └── proguard-rules.pro
├── build.gradle.kts                            # Build script root
├── settings.gradle.kts                         # Configuração do projeto
├── gradle.properties                           # Propriedades Gradle
└── README.md                                   # Este arquivo
```

## 🔧 Componentes Principais

### 1. **MainActivity.kt**
Atividade principal que gerencia a interface do usuário e a lógica de busca de CEP.

**Responsabilidades:**
- Captura entrada do CEP do usuário
- Formata o CEP automaticamente (00000-000)
- Valida o CEP em tempo real
- Faz requisições à API ViaCEP
- Preenche os campos de endereço com os dados retornados

**Campos do Formulário:**
- CEP (entrada formatada)
- Logradouro (rua/avenida)
- Bairro
- DDD (código de discagem)
- UF (estado)
- Cidade

### 2. **ViaCepService.kt**
Interface Retrofit que define a requisição HTTP para a API ViaCEP.

```kotlin
interface ViaCepService {
    @GET("{cep}/json/")
    suspend fun buscarEndereco(
        @Path("cep") cep: String
    ): ResponseEndereco
}
```

**Método:**
- `buscarEndereco(cep: String)`: Busca endereço pelo CEP (suspending function)

### 3. **ViaCepClient.kt**
Singleton que configura e fornece uma instância do cliente Retrofit.

**Configuração:**
- URL Base: `https://viacep.com.br/ws/`
- Converter: GsonConverterFactory
- Lazy initialization para melhor performance

### 4. **ResponseEndereco.kt**
Data class que representa a resposta JSON da API ViaCEP.

```kotlin
data class ResponseEndereco(
    val logradouro: String,
    val bairro: String,
    val uf: String,
    val localidade: String,
    val ddd: String
)
```

## 📱 Como Usar

### Pré-requisitos
- Android 7.0 (API 24) ou superior
- Conexão com internet (para requisições da API)

### Passos para Usar

1. **Abrir a Aplicação**: Inicie o ConsultaCep
2. **Inserir CEP**: Digite um CEP válido no campo de entrada
   - O CEP será formatado automaticamente
   - Validação acontece em tempo real
3. **Consultar**: Clique no botão "Consultar"
4. **Visualizar Dados**: Os campos de endereço serão preenchidos automaticamente

### Exemplo
```
Entrada: 01001000
Formatado: 01001-000
Resultado:
  Logradouro: Praça da Sé
  Bairro: Centro
  Cidade: São Paulo
  UF: SP
  DDD: 11
```

## 🔄 Fluxo de Dados

```
Usuário digita CEP
        ↓
Formatação automática
        ↓
Validação em tempo real
        ↓
Clica em "Consultar"
        ↓
MainActivity chama ViaCepClient.buscarEndereco()
        ↓
ViaCepService faz requisição HTTP GET
        ↓
API ViaCEP retorna JSON
        ↓
Gson converte JSON → ResponseEndereco
        ↓
MainActivity preenche campos com dados
```

## ⚙️ Configuração e Build

### Compilar o Projeto

```bash
# No diretório raiz do projeto
./gradlew build
```

### Executar no Emulador

```bash
./gradlew installDebug
```

### Gerar APK

```bash
./gradlew assembleRelease
```

### Versões do Projeto

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36
- **Version Code**: 1
- **Version Name**: 1.0

## 🌐 API ViaCEP

A aplicação consome a API pública ViaCEP que fornece informações de CEPs brasileiros.

**Endpoints Utilizados:**
- `GET /ws/{cep}/json/` - Busca endereço por CEP

**Formato de Resposta:**
```json
{
  "logradouro": "Praça da Sé",
  "bairro": "Centro",
  "localidade": "São Paulo",
  "uf": "SP",
  "ddd": "11"
}
```

**Documentação:** https://viacep.com.br/

## 📦 Dependências

### Produção
- `androidx.core:core-ktx:1.18.0` - Extensões Kotlin para Android Core
- `androidx.appcompat:appcompat` - AppCompat Library
- `com.google.android.material:material` - Material Design Components
- `androidx.activity:activity-ktx` - Atividades com Kotlin
- `androidx.constraintlayout:constraintlayout` - ConstraintLayout
- `com.squareup.retrofit2:retrofit:2.11.0` - Cliente HTTP
- `com.squareup.retrofit2:converter-gson:2.11.0` - Conversor JSON

### Testes
- `junit:junit` - Testes unitários
- `androidx.test.ext:junit` - Testes Android
- `androidx.test.espresso:espresso-core` - Testes de UI

## 🚀 Melhorias Futuras

- [ ] Adicionar testes unitários e instrumentados
- [ ] Implementar tratamento avançado de erros
- [ ] Adicionar cache local de CEPs consultados
- [ ] Implementar busca reversa (endereço → CEP)
- [ ] Adicionar suporte a temas dark/light
- [ ] Integração com Maps para visualização geográfica
- [ ] Histórico de consultas
- [ ] Compartilhamento de endereços
- [ ] Suporte offline com banco de dados local

## 🐛 Tratamento de Erros

A aplicação atualmente valida:
- ✅ CEP vazio
- ✅ CEP com menos de 8 dígitos
- ✅ Entrada de caracteres não numéricos

Melhorias recomendadas:
- Tratar erros de rede
- Validar CEPs que não existem
- Implementar retry automático
- Melhorar mensagens de erro ao usuário

## 📄 Padrões de Código

- **Arquitetura**: MVVM-inspired com ViewModels
- **Linguagem**: Kotlin puro (sem Java)
- **Async**: Coroutines e suspend functions
- **Padrões**: Singleton (ViaCepClient), Observer (TextWatcher)

## 🔐 Segurança

- A API ViaCEP é pública (sem autenticação necessária)
- Aplicação utiliza protocolo HTTPS para comunicação
- Nenhum dado sensível é armazenado localmente

## 📞 Contato e Contribuição

Desenvolvido por: **@Magalhaes-e271**

Para reportar issues ou sugerir melhorias, abra uma issue neste repositório.

## 📝 Licença

Este projeto não possui licença especificada. Verifique a política de uso antes de utilizar.

---

**Última atualização**: Setembro 2026
**Versão**: 1.0
**Kotlin**: 1.9+
**Android Target**: API 36
