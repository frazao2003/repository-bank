import { Component } from '@angular/core';
import { SidebarComponent } from "../../component/sidebar/sidebar.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ContaService } from '../../service/conta.service';
import { Router } from '@angular/router';
import { TransacoesDTO } from '../../interface/TransacoesDTO';

interface SaqueForm {
  valor: FormControl<number | null>;
  agencia: FormControl<string | null>;
  numero: FormControl<string | null>;
}

@Component({
  selector: 'app-saque',
  standalone: true,
  imports: [SidebarComponent, ReactiveFormsModule],
  templateUrl: './saque.component.html',
  styleUrl: './saque.component.scss'
})
export class SaqueComponent {
  saqueForm!: FormGroup<SaqueForm>;


  constructor(
    private router: Router,
    private contaService: ContaService,
  ){
    this.saqueForm = new FormGroup({
      valor: new FormControl<number | null>(0, [Validators.required]),
      agencia: new FormControl('', [Validators.required]),
      numero: new FormControl('', [Validators.required]),
    })
    this.saqueForm.controls.agencia.disable();
    this.saqueForm.controls.numero.disable();
  }

  agencia: string | null = null;
  numero: string | null = null;

  ngOnInit(): void {
    const agenciaSession = sessionStorage.getItem('agencia');
    if (agenciaSession) {
      this.agencia = agenciaSession;
    }
    const numeroSession = sessionStorage.getItem('numero');
    if (numeroSession) {
      this.numero = numeroSession;
    }
  }
  saqueDTO: TransacoesDTO = { agencia: '', numero: '', valor: 0};

  saque(){

    this.saqueDTO.agencia = this.agencia!;
    this.saqueDTO.numero = this.numero!;
    this.saqueDTO.valor = this.saqueForm.value.valor!;

    this.contaService.saque(this.saqueDTO).subscribe({
      next: (response) => {
        console.log('Deposito bem-sucedido:', response);
        this.router.navigate(["home"])
      },
      error: (err) => {
        console.error('Error', err);
      }
    });

  

}
}
