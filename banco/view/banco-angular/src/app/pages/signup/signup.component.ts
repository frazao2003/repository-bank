import { HttpClientModule } from '@angular/common/http';
import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { DefaultLoginLayoutComponent } from '../../component/default-login-layout/default-login-layout.component';
import { PrimaryInputComponent } from '../../component/primary-input/primary-input.component';
import { RegisterRequestDTO } from '../../interface/RegisterRequestDTO';
import { AuthService } from '../../service/auth.service';
import { UtilService } from '../../service/util.service';

interface RegisterForm {
  nome: FormControl<string | null>;
  cpf: FormControl<string | null>;
  email: FormControl<string | null>;
  observacoes: FormControl<string | null>;
  password: FormControl<string | null>;
  passwordConfirm: FormControl<string | null>;
}

@Component({
  selector: 'app-signup',
  standalone: true,
  imports: [
    DefaultLoginLayoutComponent,
    PrimaryInputComponent,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    AuthService,
  ],
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss']
})
export class SignupComponent {

  registerForm!: FormGroup<RegisterForm>;
  registerRequestDTO: RegisterRequestDTO = {} as RegisterRequestDTO;

  constructor(
    private router: Router,
    private registerService: AuthService,
    private util: UtilService
  ) {
    this.registerForm = new FormGroup({
      cpf: new FormControl('', [Validators.required, this.invalidCPF()]),
      password: new FormControl('', [Validators.required, Validators.minLength(6)]),
      nome: new FormControl('', [Validators.required, Validators.minLength(6)]),
      email: new FormControl('', [Validators.required, Validators.email]),
      observacoes: new FormControl('', []),
      passwordConfirm: new FormControl('', [Validators.required, Validators.minLength(6), this.passwordMatchValidator])
    });
  }

  invalidCPF(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const cpf = control.value; 
      if (!cpf) {
        return null;
      }
      return this.util.validaCPF(cpf) ? null : { invalidCPF: true };
    };
  }

  passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('passwordConfirm');
  
    // Verifica se ambos os campos estão definidos e se seus valores são iguais
    if (password && confirmPassword && password.value !== confirmPassword.value) {
      // Retorna um erro se os valores não são iguais
      return { 'passwordMismatch': true };
    }
  
    return null; // Retorna null se não houver erro
  }
  
  register() {
    this.registerRequestDTO = {
      password: this.registerForm.value.password!,
      cpf: this.registerForm.value.cpf!,
      email: this.registerForm.value.email!,
      observacoes: this.registerForm.value.observacoes!,
      nome: this.registerForm.value.nome!,
    };

    this.registerService.register(this.registerRequestDTO).subscribe(response => {
      console.log('Registro bem-sucedido');
      this.navigate();
    }, error => {
      console.error('Erro no registro', error);
    });

  } 

  navigate() {
    this.router.navigate(["login"]);
  }

}
