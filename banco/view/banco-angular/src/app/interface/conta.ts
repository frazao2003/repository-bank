import { Cliente } from "./cliente";

export interface Conta {

    id?: number;
    agencia: string;
    cliente: Cliente;
    numero: string;
    saldo: number
}

