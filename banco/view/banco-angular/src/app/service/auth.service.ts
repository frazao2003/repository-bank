import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginRequestDTO } from '../interface/LoginRequestDTO';
import { RegisterRequestDTO } from '../interface/RegisterRequestDTO';
import { ResponseDTO } from '../interface/ResponseDTO';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  endpoint = 'auth/';

  api = environment.api;

  constructor(private  http : HttpClient) { }

  register(body: RegisterRequestDTO) {
    const url = `${this.api}/${this.endpoint}register`;
    return this.http.post<ResponseDTO>(url, body);
  }

  login(body: LoginRequestDTO) {
    const url = `${this.api}/${this.endpoint}login`;
    return this.http.post<ResponseDTO>(url, body).pipe(
      tap((value) => {
        sessionStorage.setItem("auth-token", value.token)
        sessionStorage.setItem("cpf", value.conta.cliente.cpf)
        sessionStorage.setItem("nome", value.conta.cliente.nome)
        sessionStorage.setItem("agencia", value.conta.agencia)
        sessionStorage.setItem("numero", value.conta.numero)
        sessionStorage.setItem("email", value.conta.cliente.email)
        sessionStorage.setItem("saldo", JSON.stringify(value.conta.saldo))
      })
    )
  }

  logout() {
    sessionStorage.clear();
  }
}
