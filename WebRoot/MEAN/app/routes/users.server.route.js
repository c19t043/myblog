var users = require('../../app/controllers/users.server.controller.js');

module.exports = function(app){
	app.route('/users')
		.get(users.list)
		.post(users.create);

	app.route('/users/:userId')
		.put(users.update)
		.delete(users.delete)
		.get(users.read);

	app.param('userId',users.userById);
};
