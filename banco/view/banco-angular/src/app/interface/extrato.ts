export interface IExtrato {

    id: number;
    agenciaOrigem: string;
    numeroOrigem:string;
    agenciaDestino:string;
    numeroDestino: string;
    cpfMascaradoOrigem: string;
    cpfMascaradoDestino: string;
    nomeOrigem:string;
    nomeDestino: string;
    tipoExtrato: string;
    valor:number;
    data:Date;
}
