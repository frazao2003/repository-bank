import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { PrimaryInputComponent } from "../../component/primary-input/primary-input.component";
import { SidebarComponent } from "../../component/sidebar/sidebar.component";
import { DepositoDTO } from '../../interface/DepositoDTO';
import { ContaService } from '../../service/conta.service';
import { HomeComponent } from "../home/home.component";

interface DepositoForm {
  valor: FormControl<number | null>;
  agencia: FormControl<string | null>;
  numero: FormControl<string | null>;
}
@Component({
  selector: 'app-deposito',
  standalone: true,
  imports: [SidebarComponent, HomeComponent, PrimaryInputComponent, ReactiveFormsModule],
  templateUrl: './deposito.component.html',
  styleUrl: './deposito.component.scss'
})
export class DepositoComponent {
  depositoForm!: FormGroup<DepositoForm>;

  constructor(
    private router: Router,
    private contaService: ContaService,
  ){
    this.depositoForm = new FormGroup({
      valor: new FormControl<number | null>(0, [Validators.required]),
      agencia: new FormControl('', [Validators.required]),
      numero: new FormControl('', [Validators.required]),
    })
    this.depositoForm.controls.agencia.disable();
    this.depositoForm.controls.numero.disable();
  }
  depositoDTO: DepositoDTO = { agencia: '', numero: '', valor: 0};

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

  


  deposito(){

    this.depositoDTO.agencia = this.agencia!;
    this.depositoDTO.numero = this.numero!;
    this.depositoDTO.valor = this.depositoForm.value.valor!;

    this.contaService.deposito(this.depositoDTO).subscribe({
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
