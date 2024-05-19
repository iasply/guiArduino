// https://www.tinkercad.com/things/7zEzfOkANvo-cool-stantia/editel
float sensor = 0.0;
float constanteDeEquivalencia = 5.0 / 233.0;
float tara = 0.0;

int pinoSensor = A0;
int pinoComando = 8;
int retornoPinoComando = 0;

enum Comando
{
    TARA = 1,
    ZERAR_TARA = 2,
    SAIR = 3
};

class PainelDeComando
{
    void limparSerial()
    {
        while (Serial.available() > 0)
        {
            char dadoDescartado = Serial.read();
        }
    }

    int recebido()
    {
        bool loop = !Serial.available();

        while (loop)
        {
            if (Serial.available())
            {

                int comando = Serial.parseInt();
                if (comando != 0)
                {
                    Serial.flush();
                    return comando;
                }
                loop = true;
            }
        }
    }

public:
    PainelDeComando() {}

    void colherAcaoSerial()
    {
        bool loop = true;
        while (loop)
        {
            limparSerial();
            int comando = recebido();
            Serial.println(comando);
            switch (comando)
            {
            case ZERAR_TARA:
                tara = 0.0;
                loop = false;
                break;
            case TARA:
                tara = sensor;
                loop = false;
                break;
            default:
                loop = false;
                break;
            }
        }
    }
};

void setup()
{
    pinMode(pinoSensor, INPUT);
    pinMode(pinoComando, INPUT_PULLUP);
    Serial.begin(9600);
}

void loop()
{

    sensor = analogRead(pinoSensor);
    retornoPinoComando = digitalRead(pinoComando);

    float peso = (sensor - tara) * constanteDeEquivalencia;

    PainelDeComando painelDeComando = PainelDeComando();
    painelDeComando.colherAcaoSerial();

    Serial.print("retornoPinoComando=");
    Serial.print(retornoPinoComando);
    Serial.print(",");
    Serial.print("sensor=");
    Serial.print(sensor);
    Serial.print(",");
    Serial.print("tara=");
    Serial.print(tara);
    Serial.print(",");
    Serial.print("peso=");
    Serial.print(peso);
    Serial.print("\n\r");

    delay(1000);
}