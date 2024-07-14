import { Component } from '@angular/core';
import { SidebarComponent } from "../../component/sidebar/sidebar.component";

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [SidebarComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {

  nome: string | null = null;
  email: string | null = null; 
  saldo: string | null = null;

  ngOnInit(): void {
    // Recupera o nome do sessionStorage e atribui à propriedade nome
    const nomeSession = sessionStorage.getItem('nome');
    if (nomeSession) {
      this.nome = nomeSession; // Atribui o valor do sessionStorage se não for null
    }
    const saldoString = sessionStorage.getItem("saldo");
    const saldoSession = saldoString ? JSON.parse(saldoString) : null;
    if (saldoSession !== null) {
      this.saldo = parseFloat(saldoSession).toFixed(2); // Atribui o valor do sessionStorage se não for null
    }
  }

}
