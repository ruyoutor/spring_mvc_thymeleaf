package br.com.alura.mvc.mudi.api;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.alura.mvc.mudi.dto.RequisicaoNovaOferta;
import br.com.alura.mvc.mudi.model.Oferta;
import br.com.alura.mvc.mudi.model.Pedido;
import br.com.alura.mvc.mudi.repository.PedidoRepository;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaRest {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@PostMapping
	public Oferta criaOferta(@RequestBody RequisicaoNovaOferta requisicao) {
		
		Optional<Pedido> pedidoBuscaco = pedidoRepository.findById(requisicao.getPedidoId());
		
		if (!pedidoBuscaco.isPresent()) {
			return null;
		}
		
		Pedido pedido = pedidoBuscaco.get();
		Oferta oferta = requisicao.toOferta();
		
		pedido.getOfertas().add(oferta);
		oferta.setPedido(pedido);
		
		pedidoRepository.save(pedido);
		return oferta;
		
	}
}
