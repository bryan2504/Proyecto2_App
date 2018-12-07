class CuriosidadesJaponsController < ApplicationController
  before_action :set_curiosidades_japon, only: [:show, :edit, :update, :destroy]

  # GET /curiosidades_japons
  # GET /curiosidades_japons.json
  def index
    @curiosidades_japons = CuriosidadesJapon.all
  end

  # GET /curiosidades_japons/1
  # GET /curiosidades_japons/1.json
  def show
  end

  # GET /curiosidades_japons/new
  def new
    @curiosidades_japon = CuriosidadesJapon.new
  end

  # GET /curiosidades_japons/1/edit
  def edit
  end

  # POST /curiosidades_japons
  # POST /curiosidades_japons.json
  def create
    @curiosidades_japon = CuriosidadesJapon.new(curiosidades_japon_params)

    respond_to do |format|
      if @curiosidades_japon.save
        format.html { redirect_to @curiosidades_japon, notice: 'Curiosidades japon was successfully created.' }
        format.json { render :show, status: :created, location: @curiosidades_japon }
      else
        format.html { render :new }
        format.json { render json: @curiosidades_japon.errors, status: :unprocessable_entity }
      end
    end
  end

  # PATCH/PUT /curiosidades_japons/1
  # PATCH/PUT /curiosidades_japons/1.json
  def update
    respond_to do |format|
      if @curiosidades_japon.update(curiosidades_japon_params)
        format.html { redirect_to @curiosidades_japon, notice: 'Curiosidades japon was successfully updated.' }
        format.json { render :show, status: :ok, location: @curiosidades_japon }
      else
        format.html { render :edit }
        format.json { render json: @curiosidades_japon.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /curiosidades_japons/1
  # DELETE /curiosidades_japons/1.json
  def destroy
    @curiosidades_japon.destroy
    respond_to do |format|
      format.html { redirect_to curiosidades_japons_url, notice: 'Curiosidades japon was successfully destroyed.' }
      format.json { head :no_content }
    end
  end

  private
    # Use callbacks to share common setup or constraints between actions.
    def set_curiosidades_japon
      @curiosidades_japon = CuriosidadesJapon.find(params[:id])
    end

    # Never trust parameters from the scary internet, only allow the white list through.
    def curiosidades_japon_params
      params.require(:curiosidades_japon).permit(:tipo, :explicacion, :url_imagen, :fecha)
    end
end
