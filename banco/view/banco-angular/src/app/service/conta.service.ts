import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';
import { TransacoesDTO } from '../interface/TransacoesDTO';
import { TransferenciaDTO } from '../interface/TransferenciaDTO';

@Injectable({
  providedIn: 'root'
})
export class ContaService {

  endpoint = 'auth/';

  api = environment.api;

  constructor(private  http : HttpClient) { }

  deposito(depositoDTO: TransacoesDTO){
    const url = `${this.api}/${this.endpoint}deposito`;
    return this.http.put<void>(url, depositoDTO);
  }

  saque(saqueDTO: TransacoesDTO){
    const url = `${this.api}/${this.endpoint}saque`;
    return this.http.put<void>(url, saqueDTO);
  }

  transferencia(transferenciaDTO: TransferenciaDTO){
    const url = `${this.api}/${this.endpoint}transferencia`;
    return this.http.put<void>(url, transferenciaDTO);
  }


}
