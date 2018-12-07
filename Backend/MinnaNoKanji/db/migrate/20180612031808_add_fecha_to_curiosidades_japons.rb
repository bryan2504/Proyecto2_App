class AddFechaToCuriosidadesJapons < ActiveRecord::Migration[5.2]
  def change
    add_column :curiosidades_japons, :fecha, :string
  end
end
