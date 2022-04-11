<h1>POO-Pratico</h1>

><h2>Casa</h2>
    <p>Cada smart device deve ser identificado por um código do fabricante proveniente de um <i>HashMap</i>.</p>
    <p>Vários <i>smartDevices</i> e todos eles foram pagos para instalar quer sejam usados ou não.</p>
    <p>Cada <i>smartDevices</i> regista informações de gastos elétricos.</p>
    <p>Cada dispositivo ligado a um smartdevice pode ser ligado ou desligado remotamente.</p>
    <p>Também deve ser possível desligar todos os dispositivos de uma divisão de uma só vez.</p>

    Coleção de SmartDevices(map(String divisao, HashMap <identifier,list<SmartDevices> smartDevs))

    Métodos de cada casa:
        Ligar e desligar todos os dispositivos
        Ligar e desligar dispositivos especificos

    SmartDevices:

        SmartBulbs:
            tres modos de cor(Neutral warm cold)
            dimensão em cm
            Consumo diário
            Consumo (fórmula de cada grupo = const+tipoDeLuz)

        SmartSpeaker:
            Volume 
            Estacao 
            Marca das colunas
            Consumo Diário *
                *(fórmula de cada grupo = val_Marca_Colunas + fator_Volume_Atual (Deve ser a média diária) )

        SmartCamera:
            Resolução
            Tamanho do ficheiro onde guardam as gravações
            Consumo energético (tamanho_do_video * res_de_imagem)