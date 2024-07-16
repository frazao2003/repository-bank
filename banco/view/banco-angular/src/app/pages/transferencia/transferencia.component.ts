import { Component } from '@angular/core';
import { SidebarComponent } from "../../component/sidebar/sidebar.component";
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ContaService } from '../../service/conta.service';
import { TransferenciaDTO } from '../../interface/TransferenciaDTO';


interface TransferenciaForm {
  valor: FormControl<number | null>;
  agencia: FormControl<string | null>;
  numero: FormControl<string | null>;
  agenciaDestino: FormControl<string | null>;
  numeroDestino: FormControl<string | null>;
}
@Component({
  selector: 'app-transferencia',
  standalone: true,
  imports: [SidebarComponent, ReactiveFormsModule],
  templateUrl: './transferencia.component.html',
  styleUrl: './transferencia.component.scss'
})
export class TransferenciaComponent {
[x: string]: any;

  transferenciaForm!: FormGroup<TransferenciaForm>;


  constructor(
    private router: Router,
    private contaService: ContaService,
  ){
    this.transferenciaForm = new FormGroup({
      valor: new FormControl<number | null>(0, [Validators.required]),
      agencia: new FormControl('', [Validators.required]),
      numero: new FormControl('', [Validators.required]),
      agenciaDestino: new FormControl('', [Validators.required]),
      numeroDestino: new FormControl('', [Validators.required]),
    })
    this.transferenciaForm.controls.agencia.disable();
    this.transferenciaForm.controls.numero.disable();
  }
  transferenciaDTO: TransferenciaDTO = { agenciaOrigem: '', numeroOrigem: '',agenciaDestino: '', numeroDestino: '', valor: 0};

  agenciaOrigem: string | null = null;
  numeroOrigem: string | null = null;

  ngOnInit(): void {
    const agenciaSession = sessionStorage.getItem('agencia');
    if (agenciaSession) {
      this.agenciaOrigem = agenciaSession;
    }
    const numeroSession = sessionStorage.getItem('numero');
    if (numeroSession) {
      this.numeroOrigem = numeroSession;
    }
  }

  transferencia(){

    this.transferenciaDTO.agenciaOrigem = this.agenciaOrigem!;
    this.transferenciaDTO.numeroOrigem = this.numeroOrigem!;
    this.transferenciaDTO.valor = this.transferenciaForm.value.valor!;
    this.transferenciaDTO.agenciaDestino = this.transferenciaForm.value.agenciaDestino!;
    this.transferenciaDTO.numeroDestino = this.transferenciaForm.value.numeroDestino!;



    this.contaService.transferencia(this.transferenciaDTO).subscribe({
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
