$('#modalExclusao').on('show.bs.modal', function(event){
	const button = $(event.relatedTarget);
	const codigo = button.data('codigo');
	const descricao = button.data('descricao');
	
	const modal = $(this);
	const form = modal.find('form');
	var action = form.data('url-base');
	
	var span = modal.find('.modal-body span');
	span.html('Tem certeza que deseja excluir o título <strong>'+descricao+'<strong>?');
	
	if(!action.endsWith('/')){
		action += '/';
	}
	
	action += codigo;
	form.attr('action', action);
});

$(function(){
	$('[rel=tooltip]').tooltip();
	$('.js-currency').maskMoney({decimal:',', thousands:'.', allowZero: true});
	
	$('.js-receber').on('click', function(event){
		event.preventDefault();
		
		const botao = $(event.currentTarget);
		const href  = botao.attr('href');
		
		
		var response = $.ajax({
			url: href,
			type: 'PUT'
		});
		
		response.done(function(e){
			const codigo = botao.data('codigo');

			$('[data-codigo-recebido = ' + codigo + ']' ).text(e);
			$('[data-codigo-recebido = ' + codigo + ']' ).attr('class', 'label label-success');
			botao.hide();
			
		});
		
		response.fail(function(e){
			console.log(e);
		});
	});

});