import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../service/auth.service';
import { UtilService } from '../../service/util.service';



@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.scss'
})
export class SidebarComponent implements OnInit {
  nome: string | null = null;
  email: string | null = null; 

  constructor(
    private authService: AuthService,
    private router: Router,
    private util: UtilService
  ) { }

  ngOnInit(): void {
    // Recupera o nome do sessionStorage e atribui à propriedade nome
    const nomeSession = sessionStorage.getItem('nome');
    if (nomeSession) {
      this.nome = nomeSession; // Atribui o valor do sessionStorage se não for null
      this.nome = this.util.truncateName(this.nome);
    }
    const emailSession = sessionStorage.getItem('email');
    if (emailSession) {
      this.email = emailSession; // Atribui o valor do sessionStorage se não for null
    }

  }

  onLogout() {
    this.authService.logout();
    this.router.navigate(['/login']); // Redireciona para a página de login
  }

  navigateHome(){
    this.router.navigate(['home'])
  }
  navigateDeposito(){
    this.router.navigate(['deposito'])
  }

  

}
