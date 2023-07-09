#include <msp430.h> 

/**
 * main.c
 */
void cambio_turno();
void buttonConfig();
void Initialize_LCD();
void getSelection();
void mostrarLCD();
void mostrarSelect();
void cambiarSelect();
void mostrarValor(unsigned int i, int value);
void mostrarPantallaInicio();
void mostrarContador();
void timerConfig();
void automatico();
const unsigned char LCD_Num[10] = { 0xFC, 0x60, 0xDB, 0xF3, 0x67, 0xB7, 0xBF,
                                    0xE0, 0xFF, 0xE7 };
unsigned int select_size = 0;
unsigned int contador = 0;
unsigned int select = 0;
const int tam_matrix = 3;
const int bomb_number = 31;
int actual_number = 5;
int modo_juego = 0;
const int vector_size = tam_matrix * 2 - 2;
int number_selection[vector_size];

int fase = 0;

int main(void)
{
    WDTCTL = WDTPW | WDTHOLD;   // stop watchdog timer
    PM5CTL0 &= ~LOCKLPM5;
    P1DIR |= 0x01; // P1.0 output
    P9DIR |= 0x80;

    buttonConfig();
    Initialize_LCD();
    mostrarPantallaInicio();
    timerConfig();
    __enable_interrupt();

}

#pragma vector=PORT1_VECTOR

__interrupt void Port_l(void)
{
    if ((P1IFG & 0x02) == 0x02)
    {
        if (fase == 0)
        {
            fase++;
            modo_juego = select;
            actual_number = TA0R % (tam_matrix * tam_matrix);
            P1OUT |= 0x01;
            select_size = vector_size;
            select = 0;
            getSelection();
            mostrarLCD();

        }
        else
        {
            cambio_turno();
            if (modo_juego == 1&& contador <bomb_number)
            {
                automatico();
            }
        }
        __delay_cycles(300000);
        P1IFG &= ~BIT1;

    }
    else if ((P1IFG & 0x04) == 0x04)
    {
        cambiarSelect();
        __delay_cycles(300000);
        P1IFG &= ~0x04;
    }

}
void cambiarSelect()
{
    select = (select + 1) % select_size;
    mostrarSelect();

}

void mostrarPantallaInicio()
{
    select_size = 2;
    P1OUT &= ~0x01;
    P9OUT &= ~0x80;
    mostrarSelect();
    mostrarValor(1, LCD_Num[2]);
    mostrarValor(5, LCD_Num[1]);

}

void getSelection()
{
    // conseguir elemento de la misma columna

    // conseguir elemento de la misma fila

    int col = (actual_number - 1) % tam_matrix;
    int fila = (actual_number - 1) / tam_matrix;
    int pos = 0;
    // mover por las filas ;
    int i;

    for (i = 0; i < tam_matrix; i++)
        if (actual_number != (i * tam_matrix) + col + 1)
            number_selection[pos++] = (i * tam_matrix) + col + 1;

    for (i = 0; i < tam_matrix; i++)
        if (actual_number != (fila * tam_matrix) + i + 1)
            number_selection[pos++] = (fila * tam_matrix) + i + 1;

}

void mostrarSelect()
{
    LCDM18 |= 0x10;

    int bits = 0x80;
    int i;
    for (i = 0; i < select_size / 2; i++)
    {
        LCDM18 |= bits; // 1101   el primer y segundo bits representa el rectagunlo y el ultimo son los [ ]
        LCDM14 |= bits; //1100  // lo mismo aqui
        bits = bits >> 1;
    }
    bits = 0x80;

    for (i = 0; i < select / 2; i++)
    {
        bits = bits >> 1;
    }
    if (select % 2 == 0)
    {
        LCDM14 &= ~bits;
    }
    else
    {
        LCDM18 &= ~bits;
    }
}

void mostrarValor(unsigned int i, int value)
{
    switch (i)
    {
    case 0:
        LCDM8 = value;
        break;
    case 1:
        LCDM15 = value;
        break;
    case 2:
        LCDM19 = value;
        break;
    case 3:
        LCDM4 = value;
        break;
    case 4:
        LCDM6 = value;
        break;
    case 5:
        LCDM10 = value;
        break;
    default:
        break;
    }
}

void mostrarContador()
{
    mostrarValor(4, LCD_Num[contador % 10]);
    mostrarValor(5, LCD_Num[contador / 10]);
}

void mostrarLCD()
{
    // mostrar las opciones
    int i;
    for (i = 0; i < vector_size; i++)
    {
        mostrarValor(i, LCD_Num[number_selection[i]]);
    }

    mostrarContador();
    mostrarSelect();
// der a izq

    LCDM7 = 0x05; // 1 el punto de abajo

}

void cambio_turno()
{
    P9OUT ^= 0x80;
    P1OUT ^= 0x01;
    contador += number_selection[select];
    actual_number = number_selection[select];
    select = 0;
    getSelection();
    mostrarLCD();


    if (contador >= bomb_number)
    {
        P1OUT &= ~0x02; // P1.4 hi (pullup)
        P1REN &= ~0x02; // P1.4 pullup
    }

}

void automatico()
{
    __delay_cycles(600000);
    select = TA0R % vector_size;
    int i=1;
    while(i<vector_size && number_selection[select]+contador >= bomb_number){
        select = (select+1)%vector_size;
        i++;
    }
    cambio_turno();
}



void timerConfig()
{
    TA0CTL = TASSEL_1 | TACLR | MC_1; //configura Timer0 con el ACLK (Auxiliary Clock) como el reloj origen, resetea el timer, y lo pone en modo contar ascendente (modo up)

    TA0CCR0 = 40000; //pone el periodo de reloj a 40000

    TA0CCTL0 &= ~CCIE;

}

void buttonConfig()
{
    P1OUT |= 0x02; // P1.4 hi (pullup)
    P1REN |= 0x02; // P1.4 pullup

    P1IE |= 0x02; // P1.4 IRQ enabled
    P1IES |= 0x02; // P1.4 Hi/lo edge
    P1IFG &= ~0x02; // P1.4 IFG cleared

    P1OUT |= 0x04; // P1.1 hi (pullup)
    P1REN |= 0x04; // P1.1 pullup
    P1IE |= 0x04; // P1.1 IRQ enabled
    P1IES |= 0x04; // P1.1 Hi/lo edge
    P1IFG &= ~0x04; // P1.1 IFG cleared

}

void Initialize_LCD()
{
    PJSEL0 = BIT4 | BIT5; // For LFXT
    // Initialize LCD segments 0 - 21; 26 - 43
    LCDCPCTL0 = 0xFFFF;
    LCDCPCTL1 = 0xFC3F;
    LCDCPCTL2 = 0x0FFF;
    // Configure LFXT 32kHz crystal
    CSCTL0_H = CSKEY >> 8; // Unlock CS registers
    CSCTL4 &= ~LFXTOFF; // Enable LFXT
    do
    {
        CSCTL5 &= ~LFXTOFFG; // Clear LFXT fault flag
        SFRIFG1 &= ~OFIFG;
    }
    while (SFRIFG1 & OFIFG); // Test oscillator fault flag
    CSCTL0_H = 0; // Lock CS registers
    // Initialize LCD_C
    // ACLK, Divider = 1, Pre-divider = 16; 4-pin MUX
    LCDCCTL0 = LCDDIV__1 | LCDPRE__16 | LCD4MUX | LCDLP;
    // VLCD generated internally,
    // V2-V4 generated internally, v5 to ground
    // Set VLCD voltage to 2.60v
    // Enable charge pump and select internal reference for it
    LCDCVCTL = VLCD_1 | VLCDREF_0 | LCDCPEN;
    LCDCCPCTL = LCDCPCLKSYNC; // Clock synchronization enabled
    LCDCMEMCTL = LCDCLRM; // Clear LCD memory
    //Turn LCD on
    LCDCCTL0 |= LCDON;
    return;
}
